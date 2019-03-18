package com.roacult.kero.oxxy.projet2eme.repositories

import android.util.Log
import com.roacult.kero.oxxy.domain.AuthentificationRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.*
import com.roacult.kero.oxxy.projet2eme.local.AuthentificationLocal
import com.roacult.kero.oxxy.projet2eme.network.AuthertificationRemote
import com.roacult.kero.oxxy.projet2eme.network.entities.ConfirmationState
import com.roacult.kero.oxxy.projet2eme.network.entities.SaveInfoResult
import io.reactivex.Observable
import javax.inject.Inject

/**
 * this class will be our repository for the authentification features it implments the interface that we have defined in the doain module
 *
 */
class AutherntificationRepositoryImpl
    @Inject constructor(val remote :AuthertificationRemote , val local :AuthentificationLocal):AuthentificationRepository {
    var fname :String? = null
    /**
     * this will check the mail  and if it exist then it will send a confirmation code to the mail
     * and store that code for checking it with the code the user enter
     */
    override suspend fun checkMail(email: String): Either<Failure.SignInFaillure, MailResult> {
        var either = remote.CheckMailUser(email)
        if(either.isLeft) return either
        else {
            either = either as Either.Right<MailResult>
            fname = either.b.nom
         val either2 = remote.sendConfirmationMail(email , either.b.nom)
            if(either2.isLeft) return  either2 as Either<Failure.SignInFaillure, MailResult>
            else  {
                local.saveCodeLocal((either2 as Either.Right<String>).b)
                return either
            }
        }
    }

    /**
     * this function will the userInfo in the server so it will compress the url and convert it to base64 then
     * it will send the info to the server then if it goes well he will save it locally
     */
    override suspend fun saveUserInfo(user: UserInfo): Either<Failure.SaveInfoFaillure, None> {
        val userr = remote.mapToRequest(user)
        var either= remote.saveUserInfo(userr)
        if (either.isRight) {
            val e = either as  Either.Right<SaveInfoResult>
            local.saveUSerLocal(e.b)
            return Either.Right(None())
        }else   return either as Either<Failure.SaveInfoFaillure, None>

    }

    /**
     * this function will check the code that a user enter
     * and return if the code is correct or a failure
     * todo dont forget to verify the timing
     */

    override fun checkCodeCorrect(code:String ):Either<Failure.ConfirmEmailFaillure , None>{
        //if we use rxjava it wil be awesssooommm
        return if(local.getCounter()==5){
            remote.banneUser("user tried 5 times the confirmation code")
            Either.Left(Failure.ConfirmEmailFaillure.MaximumNumbreOfTry())
        }else{
            if(local.isCodeCorrect(code)){
                local.removeCode()
                Either.Right(None())
            }else{
                local.incrementCounter()
                Either.Left(Failure.ConfirmEmailFaillure.CadeNotCorrect())
            }
        }
    }

    /**
     * this function will log the user in and save his
     * info in the local storage
     */
    override suspend fun logUserIn(loginParam: LoginParam): Either<Failure.LoginFaillure, None> {
      val either = remote.logUserIn(loginParam)
        if(either.isLeft) return either as Either<Failure.LoginFaillure, None>
        else{
            local.saveUserLogged((either as Either.Right).b)
            return Either.Right(None())
        }
    }

    /**
     * so this funcntion will handle the resendconfirmationcode in both signUp and login features
     * so it will erase the code cashed just to be sure that no cofilt happen then it will send request to the server to
     * send an email to this adresse then if  the sending email operation works then we should store the code locally
     */
    override suspend fun resendConfirmationCode(email: String): Either<Failure.ResendConfirmationFailure, None> {
        local.removeCode()
       val either =  remote.sendConfirmationMail(email , fname)
        if(either.isLeft) {
            return if((either as Either.Left<Failure.SignInFaillure>).a  is Failure.SignInFaillure.CodeSendingError)
                Either.Left(Failure.ResendConfirmationFailure.CodeError())
            else Either.Left(Failure.ResendConfirmationFailure.OtherFailure(Throwable("erreur sending")))
        }else{
            val code =(either as Either.Right<String>).b
            local.saveCodeLocal(code)
            return Either.Right(None())
        }
    }

    /**
     * this function will handle reseting the password of a user so after making sure that the email is
     * his mail we can reset the password safely
     */
    override suspend fun resetPassword(param: ResetPasswordParams): Either<Failure.ResetPasswordFailure, None> {
        return remote.resetePassword(param)
    }

    /**
     * this function will handle sending the confirmation code to the client mail to
     * verify that he is the owner of this account and then procced to changng the password
     * so we will begin by verifying the email by the @{CheckMailUser} function and then send the code and save it locally
     */
    override suspend fun sendCodeResetPass(param: String): Either<Failure.SendCodeResetPassword, None> {
    val either = remote.CheckMailUser(param)
        if(either.isLeft){
            val failure = (either as Either.Left<Failure.SignInFaillure>).a
            when(failure){
                is Failure.SignInFaillure.AutherFaillure -> return Either.Left(Failure.SendCodeResetPassword.OtherFailrue(failure.e))
                is Failure.SignInFaillure.UserNotFoundFaillurre->return  Either.Left(Failure.SendCodeResetPassword.UserNotFound())
                is Failure.SignInFaillure.UserBanned ->return Either.Left(Failure.SendCodeResetPassword.UserBanned())
                is Failure.SignInFaillure.UserAlreadyExist->{
                     val either2 = remote.sendConfirmationMail(param , null)
                    if(either2.isLeft){
                         return Either.Left(Failure.SendCodeResetPassword.OperationFailed())
                    }else{
                        local.saveCodeLocal((either2 as Either.Right<String>).b)
                        return Either.Right(None())
                    }
                }
                else->{
                    return Either.Left(Failure.SendCodeResetPassword.OtherFailrue(null))
                }

            }
        }else{
            return Either.Left(Failure.SendCodeResetPassword.UserNotLoggedIn())
        }
    }

    /**
     * this funtion will check if the user was logged in and info saved
     */
    override suspend fun userState(): Boolean {
        return local.isUserConnected()
    }
}
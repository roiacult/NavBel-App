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
 * this class will beour repository for the authentification features it implments the interface that we have defined in the doain module
 *
 */
class AutherntificationRepositoryImpl
    @Inject constructor(val remote :AuthertificationRemote , val local :AuthentificationLocal):AuthentificationRepository {
    /**
     * this will check the mail  and if it exist then it will send a confirmation code to the mail
     * and store that code for checking it with the code the user enter
     */
    override suspend fun checkMail(email: String): Either<Failure.SignInFaillure, MailResult> {
        val either = remote.CheckMailUser(email)
        if(either.isLeft) return either
        else {
         val either2 = remote.sendConfirmationMail(email)
            if(either2.isLeft) return  either2 as Either<Failure.SignInFaillure, MailResult>
            else  {
                local.saveCodeLocal((either2 as Either.Right<String>).b)
                return either
            }
        }
    }

    /**
     * this function will the userInfo in the server so it will compress the image and convert it to base64 then
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

    override suspend fun resendConfirmationCode(email: String): Either<Failure.ResendConfirmationFailure, None> {
        local.removeCode()
       val either =  remote.sendConfirmationMail(email)
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

    override suspend fun resetPassword(param: ResetPasswordParams): Either<Failure.ResetPasswordFailure, None> {
        return remote.resetePassword(param)
    }
}
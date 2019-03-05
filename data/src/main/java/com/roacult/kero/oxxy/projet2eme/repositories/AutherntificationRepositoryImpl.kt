package com.roacult.kero.oxxy.projet2eme.repositories

import android.util.Log
import com.roacult.kero.oxxy.domain.AuthentificationRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.MailResult
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.UserInfo
import com.roacult.kero.oxxy.projet2eme.local.AuthentificationLocal
import com.roacult.kero.oxxy.projet2eme.network.AuthertificationRemote
import com.roacult.kero.oxxy.projet2eme.network.entities.SaveInfoResult
import javax.inject.Inject

class AutherntificationRepositoryImpl
    @Inject constructor(val remote :AuthertificationRemote , val local :AuthentificationLocal):AuthentificationRepository {
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

    override suspend fun saveUserInfo(user: UserInfo): Either<Failure.SaveInfoFaillure, None> {
        val userr = remote.mapToRequest(user)
        var either= remote.saveUserInfo(userr)
        Log.e("errr", "its done remotely ")
        if (either.isRight) {
            val e = either as  Either.Right<SaveInfoResult>
            local.saveUSerLocal(e.b)
            Log.e("errr", "its done actually")
            return Either.Right(None())
        }else   return either as Either<Failure.SaveInfoFaillure, None>

    }
    override fun checkCodeCorrect(code:String ):Either<Failure.ConfirmEmailFaillure , None>{
        return if(local.getCounter()==5){
            Either.Left(Failure.ConfirmEmailFaillure.MaximumNumbreOfTry())
        }else{
            if(local.isCodeCorrect(code)){
                Either.Right(None())
            }else{
                local.incrementCounter()
                Either.Left(Failure.ConfirmEmailFaillure.CadeNotCorrect())
            }
        }
    }
}
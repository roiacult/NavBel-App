package com.roacult.kero.oxxy.projet2eme.network

import android.util.Log
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.MailResult
import com.roacult.kero.oxxy.projet2eme.network.entities.Mail
import com.roacult.kero.oxxy.projet2eme.network.entities.MailResponse
import com.roacult.kero.oxxy.projet2eme.network.services.AuthentificationService
import com.roacult.kero.oxxy.projet2eme.utils.token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthertificationRemote @Inject constructor( val service: AuthentificationService){
    suspend  fun CheckMailUser(email:String):Either<Failure.SignInFaillure  , MailResult> = suspendCoroutine{
        service.checkUserMail(Mail(email)).enqueue(object :Callback<MailResponse>{
            override fun onFailure(call: Call<MailResponse>, t: Throwable) {
                Log.e("errr", t.message)
                it.resume(Either.Left(Failure.SignInFaillure.AutherFaillure(t)))
            }
            override fun onResponse(call: Call<MailResponse>, response: Response<MailResponse>) {
                val reponse =response.body()
                  if(reponse==null) {
                      it.resume(Either.Left(Failure.SignInFaillure
                          .AutherFaillure(Throwable(message = "no reponse received from the server"))))
                  }else{
                      when(reponse.reponse){
                          0->{
                                it.resume(Either.Left(Failure.SignInFaillure.UserBanned()))
                          }
                          1->{
                              it.resume(Either.Right(MailResult(reponse.year!!
                                  ,reponse.fname!!, reponse.lname!! )))
                          }
                          2->{
                              it.resume(Either.Left(Failure.SignInFaillure.UserAlreadyExist()))

                          }
                          3->{
                              it.resume(Either.Left(Failure.SignInFaillure.UserNotFoundFaillurre()))
                          }
                          else->{
                              it.resume(Either.Left(Failure.SignInFaillure.AutherFaillure(Throwable(message = "incorrect reponse"))))
                          }
                      }
                  }
            }
        })
    }


}
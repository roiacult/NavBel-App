package com.roacult.kero.oxxy.projet2eme.network

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
import kotlin.coroutines.suspendCoroutine

class AuthertificationRemote @Inject constructor( val service: AuthentificationService){
    suspend  fun CheckMailUser(email:String):Either<Failure.SignInFaillure  , MailResult> = suspendCoroutine{
        service.checkUserMail(Mail(email), token()).enqueue(object :Callback<MailResponse>{
            override fun onFailure(call: Call<MailResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<MailResponse>, response: Response<MailResponse>) {




            }
        })
    }


}
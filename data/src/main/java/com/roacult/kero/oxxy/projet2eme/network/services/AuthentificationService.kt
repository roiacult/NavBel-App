package com.roacult.kero.oxxy.projet2eme.network.services

import com.roacult.kero.oxxy.projet2eme.network.entities.Mail
import com.roacult.kero.oxxy.projet2eme.network.entities.MailResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthentificationService {
//todo configure it
@POST("check-signin.php")
fun checkUserMail(@Body mail:Mail ):Call<MailResponse>

}
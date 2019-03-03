package com.roacult.kero.oxxy.projet2eme.network.services

import com.roacult.kero.oxxy.projet2eme.network.entities.Mail
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthentificationService {
//todo configure it
@POST("")
fun checkUserMail(@Body mail:Mail , @Query("key") token:String)

}
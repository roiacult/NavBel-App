package com.roacult.kero.oxxy.projet2eme.network.services

import com.roacult.kero.oxxy.domain.interactors.UserInfo
import com.roacult.kero.oxxy.projet2eme.network.entities.Mail
import com.roacult.kero.oxxy.projet2eme.network.entities.MailResponse
import com.roacult.kero.oxxy.projet2eme.network.entities.SaveInfo
import com.roacult.kero.oxxy.projet2eme.network.entities.SaveInfoResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthentificationService {
@POST("check-signin.php")
fun checkUserMail(@Body mail:Mail ):Call<MailResponse>
    @POST("save-info.php")
    fun saveUserInfo(@Body user:SaveInfo):Call<SaveInfoResult>
}
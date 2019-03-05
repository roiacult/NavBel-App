package com.roacult.kero.oxxy.projet2eme.network.services

import com.roacult.kero.oxxy.domain.interactors.LoginParam
import com.roacult.kero.oxxy.domain.interactors.UserInfo
import com.roacult.kero.oxxy.projet2eme.network.entities.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthentificationService {
@POST("?op=check")
fun checkUserMail(@Body mail:Mail ):Call<MailResponse>
    @POST("?op=signin")
    fun saveUserInfo(@Body user:SaveInfo):Call<SaveInfoResult>
    @POST("?op=reset")
    fun sendMailConfirmation(@Body mail: Mail):Call<Code>
    @POST("?op=login")
    fun logUserIn(@Body user:LoginParam):Call<LoginResult>
}
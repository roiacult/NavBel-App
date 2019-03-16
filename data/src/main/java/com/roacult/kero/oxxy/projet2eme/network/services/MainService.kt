package com.roacult.kero.oxxy.projet2eme.network.services

import com.roacult.kero.oxxy.projet2eme.network.entities.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MainService {
    @POST("?op=challenges")
    fun getAllchallenges(@Query("tooken")token :String
     , @Body request: Request):Call<GetAllChallengeReponse>
    @POST("?op=detaille")
    fun getChallengeDetaille(@Body id :ChallengeId):Call<ChallengeDetailleReponse>
    @POST()
    fun checkChallenge(@Body id :ChallengeId):Call<CheckChallengeReponse>

}
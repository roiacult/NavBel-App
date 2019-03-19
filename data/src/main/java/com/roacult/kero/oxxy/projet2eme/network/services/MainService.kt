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
    @POST("?op=questions")
    fun getChallengeDetaille(@Body id :ChallengeId , @Query("tooken") token:String):Call<ChallengeDetailleReponse>
    @POST("?op=nbsolved")
    fun checkChallenge(@Body id :ChallengeId , @Query("tooken") token:String):Call<CheckChallengeReponse>

    @POST("")
    fun setTryChallenge(@Body data :SetUserTry , @Query("tooken") token :String
    ):Call<SetUserResponse>

}
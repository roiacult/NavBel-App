package com.roacult.kero.oxxy.projet2eme.network.services

import com.roacult.kero.oxxy.projet2eme.network.entities.GetAllChallengeReponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MainService {
    @GET()
    fun getAllchallenges(@Query("tooken")token :String):Call<GetAllChallengeReponse>
}
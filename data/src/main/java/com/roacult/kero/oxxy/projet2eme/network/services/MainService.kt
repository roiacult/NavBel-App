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

    @POST("?op=trychallenge")
    fun setTryChallenge(@Body data :SetUserTry , @Query("tooken") token :String
    ):Call<SetUserResponse>
     @POST("?op=solve")
     fun correctChallenge(@Body userAnswers: UserAnswers , @Query("tooken") token :String):Call<CorrectionResult>

    @POST("?op=profile")
    fun getUserInfo(@Body userId:UserId , @Query("tooken") token: String):Call<LoginResult>

    @POST("?op=update")
    fun updateUserInfo(@Body userInfo :UpdateUserParam , @Query("tooken") token: String):Call<UpdateUserResult>

    @POST("?op=getReward")
    fun getAwards(@Query("tooken") token:String):Call<AwardsRemote>

    @POST("?op=sendReward")
    fun getAward(@Query("tooken")  token: String ,@Body data:GetAwardRemote):Call<GetAwardResponse>
    @POST("?op=solvedChallenge")
    fun getSolvedChallenge(@Query("tooken") token: String  ,@Body data :UserId):Call<SolvedChallengesResult>

    @POST("?op=getPosts")
    fun getAllPosts(@Query("tooken") token:String ):Call<AllPostsResponse>

    @POST("?op=")
    fun createPost(@Query("tooken")  token: String , postData :CreatePostModel):Call<CreatePostResponse>
}
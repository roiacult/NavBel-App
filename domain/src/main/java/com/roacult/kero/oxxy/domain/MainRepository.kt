package com.roacult.kero.oxxy.domain

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.SubmitionParam
import com.roacult.kero.oxxy.domain.interactors.SubmitionResult
import com.roacult.kero.oxxy.domain.interactors.UpdateUserInfoParam
import com.roacult.kero.oxxy.domain.modules.*
import io.reactivex.Observable

interface MainRepository {
    suspend  fun getAllChallenges():Either<Failure.GetAllChalengesFailure , List<ChalengeGlobale> >
    fun logOut()
    suspend  fun getChallengeDetaille(challengeId:Int):Either<Failure.GetChalengeDetailsFailure ,ChalengeDetailles>
    fun checkChallenge(id:Int):Observable<Int>
    fun clearObservable()
    suspend fun setUserTry(challengeId: Int):Either<Failure.UserTryFailure , Map<Long  , Long>>
    suspend fun checkSubmit(answer:SubmitionParam ):Either<Failure.SubmitionFailure , SubmitionResult>
    suspend  fun getUserInfo():User
    suspend fun updateUserInfo(userInfoParam: UpdateUserInfoParam):Either<Failure.UpDateUserInfo , None>
    suspend fun getAwards():Either<Failure.GetAwardsFailure , List<Award>>
    suspend fun getAward(giftId:Int):Either<Failure.GetGift , None>
    suspend fun getSolvedChallenge():Either<Failure.SolvedChalengeFailure ,List<SolvedChalenge>>
    suspend fun getAllPosts():Either<Failure.PostsFailure , List<Post>>
}
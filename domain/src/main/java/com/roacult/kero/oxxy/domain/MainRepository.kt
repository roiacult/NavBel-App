package com.roacult.kero.oxxy.domain

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.SubmitionParam
import com.roacult.kero.oxxy.domain.interactors.SubmitionResult
import com.roacult.kero.oxxy.domain.modules.ChalengeDetailles
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import com.roacult.kero.oxxy.domain.modules.User
import io.reactivex.Observable

interface MainRepository {
    suspend  fun getAllChallenges():Either<Failure.GetAllChalengesFailure , List<ChalengeGlobale> >
    fun logOut()
    suspend  fun getChallengeDetaille(challengeId:Int):Either<Failure.GetChalengeDetailsFailure ,ChalengeDetailles>
    fun checkChallenge(id:Int):Observable<Int>
    fun clearObservable()
    suspend fun setUserTry(challengeId: Int):Either<Failure.UserTryFailure , None>
    suspend fun checkSubmit(answer:SubmitionParam ):Either<Failure.SubmitionFailure , SubmitionResult>
    suspend  fun getUserInfo():User
}
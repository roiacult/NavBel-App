package com.roacult.kero.oxxy.projet2eme.repositories

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.modules.ChalengeDetailles
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import com.roacult.kero.oxxy.projet2eme.local.MainLocal
import com.roacult.kero.oxxy.projet2eme.network.MainRemote
import io.reactivex.Observable
import javax.inject.Inject

/**
 * this is our main repository he will handle all business logic in the main feature local and remote
 * he implement the interface defined in the domaain layer
 */
class MainRepositoryImpl @Inject constructor( private val remote :MainRemote  ,private val local :MainLocal):MainRepository {
    override suspend fun getAllChallenges(): Either< Failure.GetAllChalengesFailure  , List<ChalengeGlobale>> {
    return remote.getChallenges(local.getChallengeRequest())
    }

    /**
     * this function will logout the user
     *
     */
    override fun logOut() {
        local.logOut()
    }

    /**
     * this function will get the challenge detailles of a challenge defined by its id
     */
    override suspend fun getChallengeDetaille(challengeId: Int): Either<Failure.GetChalengeDetailsFailure, ChalengeDetailles> {
return remote.getChallengeDetaille(challengeId)
    }

    /**
     * this function will check the challenge each 30 seconds and deliver an integer so and if the number of pasTime
     * is not equal to this number we will notify the user or we wont do thing
     *
     */
    override fun checkChallenge(id:Int): Observable<Int> {
     return remote.checkChallenge(id).filter {
         local.checkNumber(it)
     }.doOnComplete {
       local.remove()
     }.doOnNext {
         local.save(it)
     }
    }

    override fun clearObservable() {
        remote.clear()
    }

    override suspend fun setUserTry(challengeId: Int): Either<Failure.UserTryFailure, None> {
            return remote.setUserTry(local.getUserId() , challengeId)
    }
}
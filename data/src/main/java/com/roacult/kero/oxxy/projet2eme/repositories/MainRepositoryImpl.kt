package com.roacult.kero.oxxy.projet2eme.repositories

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.SubmitionParam
import com.roacult.kero.oxxy.domain.interactors.SubmitionResult
import com.roacult.kero.oxxy.domain.modules.ChalengeDetailles
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import com.roacult.kero.oxxy.projet2eme.local.MainLocal
import com.roacult.kero.oxxy.projet2eme.network.MainRemote
import com.roacult.kero.oxxy.projet2eme.network.entities.ChallengeId
import com.roacult.kero.oxxy.projet2eme.network.entities.TrueOption
import com.roacult.kero.oxxy.projet2eme.network.entities.TrueOptions
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

    /**
     * so this function will get the userID from local and run the request with the
     * @param challengeId
     */
    override suspend fun setUserTry(challengeId: Int): Either<Failure.UserTryFailure, None> {
            return remote.setUserTry(local.getUserId() , challengeId)
    }

    override suspend fun checkSubmit(answer: SubmitionParam): Either<Failure.SubmitionFailure, SubmitionResult> {
        //getting all true options for the challenge
             val gettingTrueOptionOperation = remote.getTrueOptionOfChallenge(answer.chalengeId)
        //checking for errors
        if(gettingTrueOptionOperation.isLeft){
            //if there is error return immediately
             return gettingTrueOptionOperation as Either.Left<Failure.SubmitionFailure>
        }else{
            //if there are no error we correct the answer
           val result = correctChallenge(answer = Pair(answer.answers, answer.time)
                   , bareme =
                   (gettingTrueOptionOperation as Either.Right<TrueOptions>).b  )
            if(result.success){
                //if he succed we add the number he got
               val addingPointToUser =  remote.addPointToUser(local.getUserId() , result.points)
                if(addingPointToUser.isLeft){
                    return addingPointToUser as Either.Left<Failure.SubmitionFailure >
                }else{
                    return Either.Right(result)
                }
            }else{
                //if not we return directly
                return Either.Right(result)
            }

        }
    }

    /**
     * a fair correctio function that will decide if the challenge was solved and how many point thes user will get
     * from solving the challenge
     * @param answer the user answers of the challenge
     * @param bareme the correct answers and the time of the challenge
     * @return  the result of the correction
     */
    fun correctChallenge(answer:Pair<Map<Long , Long >, Long>  , bareme:TrueOptions):SubmitionResult{
          TODO()

    }



}
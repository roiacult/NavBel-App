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
//todo dont forget to add it to local
    override suspend fun checkSubmit(answer: SubmitionParam): Either<Failure.SubmitionFailure, SubmitionResult> {
        //getting all true options for the challenge
             val gettingTrueOptionOperation = remote.getTrueOptionOfChallenge(answer.chalengeId)
        //checking for errors
        if(gettingTrueOptionOperation.isLeft){
            //if there is error return immediately
             return gettingTrueOptionOperation as Either.Left<Failure.SubmitionFailure>
        }else{
            //if there are no error we correct the answer
           val result = correctChallenge(answer = answer.answers, timeTakenPercent =answer.timeTakenPercentage
                   , bareme =
                   (gettingTrueOptionOperation as Either.Right<TrueOptions>).b.options!!)
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
     * @param bareme the correct answers and the timeTakenPercentage of the challenge
     * @return  the result of the correction
     */
    fun correctChallenge(answer:Map<Long , Long> , timeTakenPercent:Float  , bareme:List<TrueOption>):SubmitionResult{
       val userErrorNummbers = checkUserErrors(answer,bareme.associate {
           Pair(it.questionId , it.optionId)
       })
        var hasGotPenalty = false
       if(userErrorNummbers==1){
            hasGotPenalty = true
        }else if(userErrorNummbers>=2){
            return SubmitionResult(false , 0)
        }
        var pointsWithoutCountingTime = calculatePointGotFromChallenge(answer , bareme)
       if(hasGotPenalty) pointsWithoutCountingTime /= 2
        val  finalPointGot = calculateTimePenalty(timeTakenPercent , pointsWithoutCountingTime)
        return if(finalPointGot ==0L) SubmitionResult(false , 0L)
        else SubmitionResult(true , finalPointGot)

    }

    /**
     * we calculate the time penalty if the user solve it in <60% of the given time  he got all the
     * point dedicated to the challenge else for each 10%time taken mor than 60% he will be
     * cut 20% of his point
     */
    private fun calculateTimePenalty(timeTakenPercent: Float , pointGot :Long) = when {
            timeTakenPercent==1.0f -> 0L
            timeTakenPercent>1.0f -> 0L
            timeTakenPercent<=0.6f -> pointGot
            timeTakenPercent<=0.7f -> pointGot -(pointGot*2)/10
            timeTakenPercent <=0.8f -> pointGot -(pointGot*4)/10
            timeTakenPercent<=0.9f -> pointGot-(pointGot *6)/10
            else -> pointGot-(pointGot*8)/10
        }


    /**
     * we calculate the number of point the user got from solving the challenge for each
     * correct answer he got the point of that question
     */
    private fun calculatePointGotFromChallenge(answer:Map<Long , Long>  , bareme:List<TrueOption>):Long{
        var totalPoint  =0L
        val baremMap = bareme.associate {
            Pair(it.questionId , Pair(it.optionId , it.point))
        }
        answer.forEach {
            if(it.value == baremMap[it.key]!!.first){
                totalPoint += baremMap[it.key]!!.second
            }
        }
        return totalPoint

    }

    /**
     * we calculate the number of errors the user got in the challenge
     */
    private fun checkUserErrors(answer: Map<Long, Long>, barem:Map<Long , Long>):Int {
        var errorNumber = 0
        answer.forEach {
            if(barem[it.key]!=it.value){
                errorNumber++
            }
        }
        return errorNumber
    }



}
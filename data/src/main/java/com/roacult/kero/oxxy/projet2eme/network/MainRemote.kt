package com.roacult.kero.oxxy.projet2eme.network

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.SubmitionParam
import com.roacult.kero.oxxy.domain.interactors.SubmitionResult
import com.roacult.kero.oxxy.domain.modules.ChalengeDetailles
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import com.roacult.kero.oxxy.domain.modules.User
import com.roacult.kero.oxxy.projet2eme.network.entities.*
import com.roacult.kero.oxxy.projet2eme.network.services.MainService
import com.roacult.kero.oxxy.projet2eme.utils.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * this class will handle the request from the main view it will get challenge launch challenge check a challenge if he is one or not
 * ..etc
 */
open class MainRemote @Inject constructor(private val service :MainService) {

    /**
     * this will be the bucket where we put all of ou observable
     * and then we clear them when we dont need them
     */
   private  val compositeDisposable = CompositeDisposable()

    /**
     * so this function will help us request for the challenge untried by the user defined with the userID given
     * and then it can return either that the user is banned or he hasnt registred  or it can get all the challenges
     */
  suspend  fun getChallenges(request: Request):Either<Failure.GetAllChalengesFailure , List<ChalengeGlobale>> = suspendCoroutine {
      service.getAllchallenges(request =  request, token =token()).enqueue(object : Callback<GetAllChallengeReponse> {
          override fun onFailure(call: Call<GetAllChallengeReponse>, t: Throwable) {
              it.resume(Either.Left(Failure.GetAllChalengesFailure.OtherFailrue(t)))
          }

          override fun onResponse(call: Call<GetAllChallengeReponse>, response: Response<GetAllChallengeReponse>) {
             val reponse = response.body()
           when{
               reponse==null -> it.resume(Either.Left(Failure.GetAllChalengesFailure.OtherFailrue(Throwable("reponse incorrect"))))
               reponse.reponse==0-> it.resume(Either.Left(Failure.GetAllChalengesFailure.UserBannedForever))
               reponse.reponse==1->if(reponse.challenges==null) it.resume(Either.Left(Failure.GetAllChalengesFailure.OtherFailrue(Throwable("reponse incorrect"))))
               else it.resume(Either.Right(reponse.challenges))
               reponse.reponse== 2-> it.resume(Either.Left(Failure.GetAllChalengesFailure.UserBannedTemp))
               reponse.reponse== -1-> it.resume(Either.Left(Failure.GetAllChalengesFailure.OperationFailed))
           }
          }
      })

  }

    /**
     * this funtion will send request to get the challenge detaille a challenge detaille is the timeTakenPercentage that  a challenge can take
     * and the id of the challenge and the ressources with this challenges
     */
    suspend  fun getChallengeDetaille(id:Int ):Either<Failure.GetChalengeDetailsFailure , ChalengeDetailles> = suspendCoroutine{
   service.getChallengeDetaille(ChallengeId(id), token() ).enqueue(object :Callback<ChallengeDetailleReponse>{
       override fun onFailure(call: Call<ChallengeDetailleReponse>, t: Throwable) {
           it.resume(Either.Left(Failure.GetChalengeDetailsFailure.OtherFailrue(t)))
       }

       override fun onResponse(call: Call<ChallengeDetailleReponse>, response: Response<ChallengeDetailleReponse>) {
           val reponse = response.body()
           when{
               reponse==null -> it.resume(Either.Left(Failure.GetChalengeDetailsFailure.OtherFailrue(Throwable("reponse incorrect"))))
               reponse.reponse==-1-> it.resume(Either.Left(Failure.GetChalengeDetailsFailure.ChallengeAlreadySolved))
               reponse.reponse==1->if((reponse.questions==null) or (reponse.resource==null))
                   it.resume(Either.Left(Failure.GetChalengeDetailsFailure.OtherFailrue(Throwable("reponse incorrect"))))
               else it.resume(Either.Right(ChalengeDetailles(id
                   , reponse.time!! ,
                   reponse.resource?.fromRessourceToPair()!! ,
                   reponse.questions?.mapToQuestion()!!)))
               reponse.reponse== 2-> it.resume(Either.Left(Failure.GetChalengeDetailsFailure.UserBannedTemp))
           }
       }
   })

    }

    /**
     * so this function will be called when the challenge start so this function will push an observable to notify observer
     * each 30 seconds and when it notify it will send a request to the server to check if the  challenge is solved and how much
     * person solved it
     */
      fun checkChallenge(id:Int):Observable<Int>{
          val subject = BehaviorSubject.create<Int>()
          val observable = Observable.interval(30, TimeUnit.SECONDS).map {
            service.checkChallenge(ChallengeId(id) , token()).enqueue(object:Callback<CheckChallengeReponse>{
                override fun onFailure(call: Call<CheckChallengeReponse>, t: Throwable) {
                    subject.onError(t)
                }

                override fun onResponse(call: Call<CheckChallengeReponse>, response: Response<CheckChallengeReponse>) {
                    val reponse = response.body()
                    if(reponse!=null){
                        if(reponse.reponse==1){
                            subject.onNext(reponse.nbPersonSolved!!)
                        }else if(reponse.reponse==-1){
                            subject.onComplete()
                        }
                    }
                }
            })
          }
          compositeDisposable.add(observable.publish().connect())
        return subject.toFlowable(BackpressureStrategy.DROP).toObservable()
      }

    /**
     * this function will clear the connection between the observer and observable so the observable stop emitting
     */
    fun clear(){
        compositeDisposable.clear()
    }

    /**
     * this function will lunch a post request to the server teling him that the user withe
     * @param userId has tried one timeTakenPercentage in the challenge with the
     * @param challengeId so he cant retry next timeTakenPercentage
     *
     */
    suspend fun setUserTry(userId :Long , challengeId:Int ):Either<Failure.UserTryFailure,None> = suspendCoroutine{
        service.setTryChallenge(SetUserTry(userId , challengeId), token()).enqueue(object :Callback<SetUserResponse>{
            override fun onFailure(call: Call<SetUserResponse>, t: Throwable) {
                it.resume(Either.Left(Failure.UserTryFailure.OtherFailure(t))) }

            override fun onResponse(call: Call<SetUserResponse>, response: Response<SetUserResponse>) {
           if(response.body()?.reponse==1){
               it.resume(Either.Right(None()))
           }else{
               it.resume(Either.Left(Failure.UserTryFailure.ChallengeAlreadySolved))
           }
            }
        })

    }

    /**
     *sending request to correct challenges
     * @param submitionResult will hold the challengeId  and the user answers of the challenge questions
     * @param userId the userId that refer to the user who has solved this challenge
     * @author akram09
     */
    suspend fun correctChallenge( submitionResult: SubmitionParam , userId: Long):
            Either<Failure.SubmitionFailure , SubmitionResult> =
        service.correctChallenge(mapDomainParamToDataEntities(submitionResult, userId)
            , token()).lambdaEnqueue(
            {
                it.printStackTrace()
        Either.Left(Failure.SubmitionFailure.UknownFailure(it))
            })
        {
            mapApiResponseToDomainResponse(it)
        }

    /**
     * map api response to domain entity
     */
    private fun mapApiResponseToDomainResponse(response: Response<CorrectionResult>)
    :Either<Failure.SubmitionFailure, SubmitionResult>{
        val reponse = response.body()
        return if(reponse==null){
            Either.Left(Failure.SubmitionFailure.UknownFailure(Throwable("empty response")))
        }else{
            mapCorrResToSubmitRes(reponse)
        }
    }

    /**
     * map response  to domain entity
     * response 0 ->  the user has cheated
     * 1 -> valid operation everything got well
     * -1 -> backend problem
     */
    private fun mapCorrResToSubmitRes(correctionResult: CorrectionResult):Either<Failure.SubmitionFailure , SubmitionResult>{
       return when(correctionResult.reponse){
            0-> Either.Left(Failure.SubmitionFailure.CheaterFailure)
            1->Either.Right(SubmitionResult(correctionResult.playerPoint!=0L ,correctionResult.playerPoint))
            -1->Either.Left(Failure.SubmitionFailure.GetTrueOptionOperationFailure)
            else->Either.Left(Failure.SubmitionFailure.UknownFailure(Throwable("Invalid response")))
        }
    }

    /**
     * map domain param to api body request
     */
    private fun mapDomainParamToDataEntities(submitionResult: SubmitionParam , userId: Long) = UserAnswers(submitionResult.chalengeId.toLong() ,
        userId , mapPercentageToLong(submitionResult.timeTakenPercentage), mapAnwersToList(submitionResult.answers)
        )
    private fun mapPercentageToLong(percent :Float)=(percent*10).toLong()
    private fun mapAnwersToList(map:Map<Long , Long >)= map.toList().map {
        QuestionAnswer(it.first , it.second)
    }

}
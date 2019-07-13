package com.roacult.kero.oxxy.projet2eme.network

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.*
import com.roacult.kero.oxxy.domain.interactors.QuestionAnswer
import com.roacult.kero.oxxy.domain.modules.Award
import com.roacult.kero.oxxy.domain.modules.ChalengeDetailles
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import com.roacult.kero.oxxy.projet2eme.network.entities.*
import com.roacult.kero.oxxy.projet2eme.network.entities.SetUserTry
import com.roacult.kero.oxxy.projet2eme.network.services.MainService
import com.roacult.kero.oxxy.projet2eme.utils.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * this class will handle the request from the main view it will get challenge launch challenge check a challenge if he is one or not
 * ..etc
 */
open class MainRemote @Inject constructor(private val service :MainService , private  val context: Context) {

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
                    ,
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
    suspend fun setUserTry(userId :Int , challengeId:Int ):Either<Failure.UserTryFailure,Map<Long , Long >> = suspendCoroutine{
        service.setTryChallenge(SetUserTry(userId , challengeId), token()).enqueue(object :Callback<SetUserResponse>{
            override fun onFailure(call: Call<SetUserResponse>, t: Throwable) {
                Log.e("errr", t.localizedMessage)
                it.resume(Either.Left(Failure.UserTryFailure.OtherFailure(t))) }

            override fun onResponse(call: Call<SetUserResponse>, response: Response<SetUserResponse>) {
                val reponse = response.body()
           if(reponse?.reponse==1){
               it.resume(Either.Right(reponse.data.mapRemoteAnswersToDomain()))
           }else{
               it.resume(Either.Left(Failure.UserTryFailure.ChallengeAlreadySolved))
           }
            }
        })

    }

    /**
     *sending request to correct challenges
     * @param submitionResult will hold the challengeId  and the user data of the challenge questions
     * @param userId the userId that refer to the user who has solved this challenge
     * @author akram09
     */
    suspend fun correctChallenge( submitionResult: SubmitionParam , userId: Int):
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
            1->Either.Right(SubmitionResult(correctionResult.totalwin!=0L ,correctionResult.totalwin))
            -1->Either.Left(Failure.SubmitionFailure.GetTrueOptionOperationFailure)
            else->Either.Left(Failure.SubmitionFailure.UknownFailure(Throwable("Invalid response")))
        }
    }

    /**
     * map domain param to api body request
     */
    private fun mapDomainParamToDataEntities(submitionResult: SubmitionParam , userId: Int) = UserAnswers(submitionResult.chalengeId.toLong() ,
        userId , mapAnwersToList(submitionResult.answers)
        )
    private fun mapAnwersToList(map:Map<Long , QuestionAnswer >)= map.toList().map {
        com.roacult.kero.oxxy.projet2eme.network.entities.RemoteQuestionAnswer(it.first , it.second.optionId , it.second.isFinished)
    }

    /**
     * getting user Info from api
     * @param userId the  id of the user
     */
    suspend fun getUserInfoFromRemote(userId: Int):Either<Failure.GetUserInfoFailure , LoginResult>
           = service.getUserInfo(UserId(userId) , token())
        .lambdaEnqueue({
           Either.Left(Failure.GetUserInfoFailure.OperationFailed)

                       })
             {
                 mapServerRestoUsrInfoResp(it)

             }
    private fun mapServerRestoUsrInfoResp(response: Response<LoginResult>) :Either<Failure.GetUserInfoFailure , LoginResult>{
        val reponseBody  = response.body()
       return  if(reponseBody!=null){
            if(reponseBody.reponse==1){
            Either.Right(reponseBody)
            }else{
                Either.Left(Failure.GetUserInfoFailure.OperationFailed)

            }
        }else{
             Either.Left(Failure.GetUserInfoFailure.OperationFailed)
        }
    }

    /**
     * updating user info
     */
    suspend  fun updateUserInfo(updateUserInfoParam: UpdateUserInfoParam , userId: Int):Either<Failure.UpDateUserInfo , String>
       = service.updateUserInfo(
        UpdateUserParam(userId , updateUserInfoParam.fname , updateUserInfoParam.lName
        ,  //  if the user hasnt changed his image then  the picture will be null
             updateUserInfoParam.picture?.run {
                val baos = ByteArrayOutputStream()
                val file = File(this)
                val bitmap =  MediaStore.Images.Media.getBitmap(context.contentResolver , Uri.fromFile(file))
                bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    50, baos)
                val b = baos.toByteArray()
                //picture encoded to bas64
                Base64.encodeToString(b, Base64.DEFAULT)
            }   ?: "" , updateUserInfoParam.public , updateUserInfoParam.password), token())
        .lambdaEnqueue({
            it.printStackTrace()
            return@lambdaEnqueue  Either.Left(Failure.UpDateUserInfo.OperationFailed)
        }){
            val responseBody =  it.body()
           if(responseBody==null) {
               return@lambdaEnqueue  Either.Left(Failure.UpDateUserInfo.OperationFailed)
             }else{
                 if(responseBody.reponse==1){
                     return@lambdaEnqueue Either.Right(responseBody.picture)
                 }else{
                     return@lambdaEnqueue Either.Left(Failure.UpDateUserInfo.OperationFailed)
                 }
             }


        }

    /**
     * this fonction will get all the rewards from the remote database
     */
    suspend fun getRewards():Either<Failure.GetAwardsFailure , List<Award>>{
        return service.getAwards(token()).lambdaEnqueue({
            Log.e("errr", it.localizedMessage)
            Either.Left(Failure.GetAwardsFailure)
        }, {
            val body = it.body()
            if(body==null){
                Either.Left(Failure.GetAwardsFailure)
            }else{
                 Either.Right(body.data.map {
                     Award(it.id ,it.image , it.point , it.description)
                 })
            }
        })
    }

    suspend fun getAward(userId:Int , giftId:Int):Either<Failure.GetGift , None>{
        return service.getAward(token() , GetAwardRemote(userId , giftId)).lambdaEnqueue({
            Log.e("errr", it.localizedMessage)
            Either.Left(Failure.GetGift) as Either<Failure.GetGift, None>
        }){
            val body = it.body()
            body?.apply {
                if(response==1){
                    return@lambdaEnqueue Either.Right(None())
                }
            }
            return@lambdaEnqueue Either.Left(Failure.GetGift)
        }
    }
}
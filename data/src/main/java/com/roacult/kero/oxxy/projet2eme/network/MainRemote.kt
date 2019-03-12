package com.roacult.kero.oxxy.projet2eme.network

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import com.roacult.kero.oxxy.projet2eme.network.entities.GetAllChallengeReponse
import com.roacult.kero.oxxy.projet2eme.network.entities.Request
import com.roacult.kero.oxxy.projet2eme.network.services.MainService
import com.roacult.kero.oxxy.projet2eme.utils.token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * this class will handle the request from the main view it will get challenge launch challenge check a challenge if he is one or not
 * ..etc
 */
class MainRemote @Inject constructor(val service :MainService) {
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
               reponse.reponse==3 -> it.resume(Either.Left(Failure.GetAllChalengesFailure.UserNotRegistred))
               reponse.reponse==4-> it.resume(Either.Left(Failure.GetAllChalengesFailure.UserBannedTemp))
           }
          }
      })



  }
}
package com.roacult.kero.oxxy.projet2eme.utils

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.Option
import com.roacult.kero.oxxy.domain.modules.Question
import com.roacult.kero.oxxy.projet2eme.network.entities.QuestionReponse
import com.roacult.kero.oxxy.projet2eme.network.entities.Ressource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun List<Ressource>.fromRessourceToPair():List<Pair<String , String>> = this.map {
    Pair(it.name , it.url)
}
fun List<QuestionReponse>.mapToQuestion():List<Question>{
    //TODO change it and add question isFinished
   return  this.map {
        Question(it.id.toLong(), it.question , it.options.map {
            Option(it.id.toLong() , it.trueoption)
        },/*TODO change this*/1000)
    }
}
suspend  fun  <T , F: Failure, R> Call<R>.lambdaEnqueue( onFailure:(t:Throwable)->Either<F , T>
                                                        , onSuccess:(response:Response<R>)->Either<F , T>
                                       ):Either<F , T> =
    suspendCoroutine {
        this.enqueue(object :Callback<R>{
            override fun onFailure(call: Call<R>, t: Throwable) {
                it.resume(onFailure(t))
            }

            override fun onResponse(call: Call<R>, response: Response<R>) {
               it.resume(onSuccess(response))
            }
        })
}
package com.roacult.kero.oxxy.projet2eme.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.Option
import com.roacult.kero.oxxy.domain.modules.Question
import com.roacult.kero.oxxy.projet2eme.network.entities.QuestionReponse
import com.roacult.kero.oxxy.projet2eme.network.entities.Ressource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun List<Ressource>.fromRessourceToPair():List<Pair<String , String>> = this.map {
    Pair(it.name , it.url)
}
fun List<QuestionReponse>.mapToQuestion():List<Question>{
   return  this.map {
        Question(it.id.toLong(), it.question , it.options.map {
            Option(it.id.toLong() , it.trueoption)
        },it.time)
    }
}
fun String?.compressConvertBase64(context: Context):String?
    = this?.run {
        val baos = ByteArrayOutputStream()
        val file = File(this)
        val bitmap =  MediaStore.Images.Media.getBitmap(context.contentResolver , Uri.fromFile(file))
        bitmap.compress(
            Bitmap.CompressFormat.JPEG,
            50, baos)
        val b = baos.toByteArray()
        //picture encoded to bas64
     Base64.encodeToString(b, Base64.DEFAULT)
    }
fun <L , R , NR>Either<L , R>.mapRight(fnR:(R)->NR):Either<L , NR>
        =  when(this){
    is Either.Left-> this
    is Either.Right ->  Either.Right(fnR(b))
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
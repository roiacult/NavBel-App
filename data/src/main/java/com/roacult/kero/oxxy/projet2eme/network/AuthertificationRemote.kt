package com.roacult.kero.oxxy.projet2eme.network

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.MailResult
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.UserInfo
import com.roacult.kero.oxxy.projet2eme.network.services.AuthentificationService
import com.roacult.kero.oxxy.projet2eme.utils.token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import android.graphics.Bitmap
import com.roacult.kero.oxxy.domain.interactors.LoginParam
import com.roacult.kero.oxxy.projet2eme.network.entities.*
import com.roacult.kero.oxxy.projet2eme.utils.toHexString
import java.io.ByteArrayOutputStream
import java.security.MessageDigest


/**
 * this class will handle the authentification remote requests
 */
class AuthertificationRemote @Inject constructor( val service: AuthentificationService , val context:Context){
    /**
     * this function will send a request to a server this request will have an email which the server will check if
     *  the user is banned forever or he has already subscribed or he doesnt exist in the students table
     */
    suspend  fun CheckMailUser(email:String):Either<Failure.SignInFaillure  , MailResult> = suspendCoroutine{
        service.checkUserMail(Mail(email)).enqueue(object :Callback<MailResponse>{
            override fun onFailure(call: Call<MailResponse>, t: Throwable) {
                it.resume(Either.Left(Failure.SignInFaillure.AutherFaillure(t)))
            }
            override fun onResponse(call: Call<MailResponse>, response: Response<MailResponse>) {
                val reponse =response.body()
                  if(reponse==null) {
                      it.resume(Either.Left(Failure.SignInFaillure
                          .AutherFaillure(Throwable(message = "no reponse received from the server"))))
                  }else{
                      when(reponse.reponse){
                          0->{
                                it.resume(Either.Left(Failure.SignInFaillure.UserBanned()))
                          }
                          1->{
                              it.resume(Either.Right(MailResult(reponse.year!!
                                  ,reponse.fname!!, reponse.lname!! )))
                          }
                          2->{
                              it.resume(Either.Left(Failure.SignInFaillure.UserAlreadyExist()))

                          }
                          3->{
                              it.resume(Either.Left(Failure.SignInFaillure.UserNotFoundFaillurre()))
                          }
                          else->{
                              it.resume(Either.Left(Failure.SignInFaillure.AutherFaillure(Throwable(message = "incorrect reponse"))))
                          }
                      }
                  }
            }
        })
    }

    /**
     * this function will take the userInfo as param and post this param to the server and then it returnn either a failure
     * or a none (so it has completed successfully)
     */
    suspend fun saveUserInfo(userInfo: SaveInfo):Either<Failure.SaveInfoFaillure , SaveInfoResult> = suspendCoroutine {
        service.saveUserInfo(userInfo).enqueue(object :Callback<SaveInfoResult>{
            override fun onFailure(call: Call<SaveInfoResult>, t: Throwable) {
                Log.e("errr", "errrrroor")
                  it.resume(Either.Left(Failure.SaveInfoFaillure.OtherFailure(t)))
            }

            override fun onResponse(call: Call<SaveInfoResult>, response: Response<SaveInfoResult>) {
                 val reponse = response.body()
                if(reponse==null) it.resume(Either.Left(Failure.SaveInfoFaillure.OtherFailure(Throwable(message = "we have a server error"))))
                else{
                    if(reponse.reponse==0){
                        it.resume(Either.Left(Failure.SaveInfoFaillure.OperationFailed()))
                    }else{
                        it.resume(Either.Right(reponse))
                    }
                }
            }
        })
    }

    suspend fun sendConfirmationMail(email:String):Either<Failure.SignInFaillure , String> = suspendCoroutine{
        service.sendMailConfirmation(Mail(email)).enqueue(object :Callback<Code>{
            override fun onFailure(call: Call<Code>, t: Throwable) {
                it.resume(Either.Left(Failure.SignInFaillure.AutherFaillure(t)))
            }

            override fun onResponse(call: Call<Code>, response: Response<Code>) {
                val reponse = response.body()
                if(reponse==null){
                    it.resume(Either.Left(Failure.SignInFaillure.AutherFaillure(Throwable("server eror"))))
                }else {
                    if (reponse.reponse == "0"){
                        it.resume(Either.Left(Failure.SignInFaillure.CodeSendingError()))
                    }else {
                        it.resume(Either.Right(reponse.reponse))
                    }
                    }
            }
        })
    }
    /**
     * this function will map the userInfo to the saveInfo  the userInfo object will be get from the presentation layer and the saveInfo
     * will be the object that we will send to the server the difference between them is that the picture can be null and converted to
     * an empty string or it can have the uri and will be converted to base64
     */
    suspend fun mapToRequest(  userInfo: UserInfo):SaveInfo = suspendCoroutine{
        var picture :String
        //if ther picture is null it will be converted to an empty string
        if(userInfo.pictureUrl==null) picture = ""
        else{
            picture =userInfo.pictureUrl!!
            // the picture will be compressed here
            val baos = ByteArrayOutputStream()
            MediaStore.Images.Media.getBitmap(context.contentResolver , Uri.fromFile(File(picture))).compress(Bitmap.CompressFormat.PNG,
                100, baos)

            val b = baos.toByteArray()
            //picture encoded to bas64
            picture  = Base64.encodeToString(b, Base64.URL_SAFE or Base64.NO_WRAP)
        }
        it.resume( SaveInfo(userInfo.email , userInfo.fName ,userInfo.lName ,userInfo.pass ,
            toHexString(picture.toByteArray()), userInfo.year))
    }



    suspend fun logUserIn(user:LoginParam):Either<Failure.LoginFaillure , LoginResult> = suspendCoroutine {
        service.logUserIn(user).enqueue(object :Callback<LoginResult>{
            override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                it.resume(Either.Left(Failure.LoginFaillure.AutherFaillure(t)))
            }

            override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
                 val reponse = response.body()
                if(reponse==null) it.resume(Either.Left(Failure.LoginFaillure.AutherFaillure(Throwable("the reponse is null"))))
                  else {
                    //user doesnt exist or unsaved
                    if(reponse.reponse==0){
                        it.resume(Either.Left(Failure.LoginFaillure.UserNotSubscribedYet()))

                    }else  ///the user is subscribed and logged in
                        if(reponse.reponse==1){
                        it.resume(Either.Right(reponse))
                    }else if(reponse.reponse==2){
                            it.resume(Either.Left(Failure.LoginFaillure.WrongPassword()))
                        }else if(reponse.reponse==3){
                            it.resume(Either.Left(Failure.LoginFaillure.UserBanned()))
                        }
                }
            }
        })

    }
    fun banneUser(reason:String){
          service.banneUser(BanneParam(reason)).enqueue(object :Callback<BanneResult>{
              override fun onFailure(call: Call<BanneResult>, t: Throwable) {
                 t.printStackTrace()
              }

              override fun onResponse(call: Call<BanneResult>, response: Response<BanneResult>) {
                       if(response.body()?.reponse==0){
                           Log.e("errr","user is not banned" )
                       }else{
                           Log.e("errr", "user is banned now")
                       }
              }
          })
    }
}

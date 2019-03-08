package com.roacult.kero.oxxy.projet2eme.network

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
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
import com.roacult.kero.oxxy.domain.interactors.*
import com.roacult.kero.oxxy.projet2eme.network.entities.*
import com.roacult.kero.oxxy.projet2eme.utils.toHexString
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.io.ByteArrayOutputStream
import java.security.MessageDigest
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit


/**
 * this class will handle the authentification remote requests
 */
class AuthertificationRemote @Inject constructor( val service: AuthentificationService , val context:Context){
    /**
     * this function will send a request to a server this request will have an email which the server will check if
     *  the user is banned forever or he has already subscribed or he doesnt exist in the students table
     */
    suspend  fun CheckMailUser(email:String):Either<Failure.SignInFaillure  , MailResult> = suspendCoroutine{
        service.checkUserMail(Mail(email) , token()).enqueue(object :Callback<MailResponse>{
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
        service.saveUserInfo(userInfo , token()).enqueue(object :Callback<SaveInfoResult>{
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

    suspend fun sendConfirmationMail(email:String , fname:String?):Either<Failure.SignInFaillure , String> = suspendCoroutine{
        service.sendMailConfirmation(CofirmMail(0 , "", email , fname), token()).enqueue(object :Callback<Code>{
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
            MediaStore.Images.Media.getBitmap(context.contentResolver , Uri.fromFile(File(picture)))
                .compress(Bitmap.CompressFormat.JPEG,
                100, baos)
            //todo set progress here

            val b = baos.toByteArray()
            //picture encoded to bas64
            picture  = Base64.encodeToString(b, Base64.URL_SAFE or Base64.NO_WRAP)
        }
        it.resume( SaveInfo(userInfo.email , userInfo.fName ,userInfo.lName ,userInfo.pass ,
            toHexString(picture.toByteArray()), userInfo.year))
    }


    /**
     * this function will handle the login feature so it will send a log in request with  a user and a password
     * it will then response with either the user info or  a failure
     */
    suspend fun logUserIn(user:LoginParam):Either<Failure.LoginFaillure , LoginResult> = suspendCoroutine {
        service.logUserIn(LoginParame(0 , "", user.email , user.password), token()).enqueue(object :Callback<LoginResult>{
            override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                it.resume(Either.Left(Failure.LoginFaillure.AutherFaillure(t)))
            }

            override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
                 val reponse = response.body()
                if(reponse==null) it.resume(Either.Left(Failure.LoginFaillure.AutherFaillure(Throwable("the reponse is null"))))
                  else {
                    //user doesnt exist or unsaved
                    if(reponse.reponse==0){
                        it.resume(Either.Left(Failure.LoginFaillure.UserBanned()))
                    }else  ///the user is subscribed and logged in
                        if(reponse.reponse==1){
                        it.resume(Either.Right(reponse))
                    }else if(reponse.reponse==2){
                            it.resume(Either.Left(Failure.LoginFaillure.UserNotSubscribedYet()))
                        }else if(reponse.reponse==3){
                             it.resume(Either.Left(Failure.LoginFaillure.NotFromEsi()))
                        }else if(reponse.reponse==4){
                            it.resume(Either.Left(Failure.LoginFaillure.WrongPassword()))

                        }
                }
            }
        })

    }

    /**
     * this function will send a request to the server to banne this user because of the reason we give to it
     *
     */
    fun banneUser(reason:String){
          service.sendMailConfirmation(CofirmMail(1 ,reason , "" ), token()).enqueue(object :Callback<Code> {
              override fun onFailure(call: Call<Code>, t: Throwable) {
                          t.printStackTrace()
              }

              override fun onResponse(call: Call<Code>, response: Response<Code>) {
                       Log.e("errr", "done")
              }
          }
              )
    }

    /**
     * this function will send a put request to the server to reset the password of the giving mail
     */
    suspend fun resetePassword(param:ResetPasswordParams):Either<Failure.ResetPasswordFailure , None> = suspendCoroutine {
        service.resetePassword(param , token()).enqueue(object :Callback<Reponse>{
            override fun onFailure(call: Call<Reponse>, t: Throwable) {
                it.resume(Either.Left(Failure.ResetPasswordFailure.OtherFailure(t)))
            }

            override fun onResponse(call: Call<Reponse>, response: Response<Reponse>) {
                if(response.body()?.reponse==1){
                    it.resume(Either.Right(None()))
                }else{
                    it.resume(Either.Left(Failure.ResetPasswordFailure.OperationFailed()))
                }
            }
        })




    }
}

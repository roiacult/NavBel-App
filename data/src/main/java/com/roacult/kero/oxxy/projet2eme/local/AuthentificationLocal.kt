package com.roacult.kero.oxxy.projet2eme.local

import android.content.SharedPreferences
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.projet2eme.network.entities.SaveInfoResult
import com.roacult.kero.oxxy.projet2eme.utils.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * this class will handle  the local authentification
 */

class AuthentificationLocal @Inject constructor(val preference:SharedPreferences) {

    suspend fun saveUSerLocal(user:SaveInfoResult):Either<Failure.SaveInfoFaillure , None> = suspendCoroutine{
         preference.edit().apply {
             putString(USER_EMAIL , user.email)
             putString(USER_IMAGEURL , user.imageUrl)
             putString(USER_NAME , user.fname)
             putString(USER_PRENAME , user.lname)
            commit()
         }
        it.resume(Either.Right(None()))
    }
    fun saveCodeLocal(code :String){
        preference.edit().apply{
            putString(USER_CODE , code)
            putInt(USER_COUNTER  , 0)
            commit()
        }
    }
    fun incrementCounter(){
        preference.edit().apply{
            putInt(USER_COUNTER , preference.getInt(USER_COUNTER , 0)+1)
            commit()
        }
    }
    fun getCounter():Int{
       return  preference.getInt(USER_COUNTER , 0)
    }
    fun isCodeCorrect(code:String):Boolean{
       return  preference.getString(USER_CODE, "").equals(code)
    }
}
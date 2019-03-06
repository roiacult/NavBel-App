package com.roacult.kero.oxxy.projet2eme.local

import android.content.SharedPreferences
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.projet2eme.network.entities.LoginResult
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
             putBoolean(USER_CONNECTED , true )
             putInt(USER_POINT , 0)
             putInt(USER_RANK ,0)
             putInt(NQSOLVED , 0)
             //todo date
             //rank table
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

    fun removeCode(){
        preference.edit().apply {
            remove(USER_CODE)
            remove(USER_COUNTER)
            commit()
        }
    }
    fun saveUserLogged(info:LoginResult){
        preference.edit().apply{
            putString(USER_EMAIL , info.email)
            putString(USER_IMAGEURL , info.picture)
            putString(USER_NAME , info.fname)
            putString(USER_PRENAME , info.lname)
            putBoolean(USER_CONNECTED , true )
            putInt(USER_POINT , info.point ?: 0)
            putInt(USER_RANK ,info.currentrank ?: 0)
            putInt(NQSOLVED , info.nbsolved ?: 0)
            putString(DATE , info.date)
//            putStringSet()
            commit()
        }
    }
}
package com.roacult.kero.oxxy.projet2eme.local

import android.content.SharedPreferences
import android.util.Log
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.projet2eme.network.entities.LoginResult
import com.roacult.kero.oxxy.projet2eme.network.entities.SaveInfoResult
import com.roacult.kero.oxxy.projet2eme.utils.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * this class will handle  the local authentification
 */

class AuthentificationLocal @Inject constructor(preference:SharedPreferences):LocalStorage(preference) {

    /**
     * save the user data localy
     */
    suspend fun saveUSerLocal(user:SaveInfoResult) =
         modify {
             putString(USER_EMAIL, user.email)
             putString(USER_IMAGEURL, user.picture)
             putString(USER_NAME, user.fname)
             putString(USER_PRENAME, user.lname)
             putBoolean(USER_CONNECTED, true)
             putInt(USER_POINT, 0)
             putInt(USER_RANK, 0)
             putInt(NQSOLVED, 0)
             putString(USER_DATE, user.date)
             putInt(USER_YEAR, user.year ?: 0)
             putInt(USER_ID, user.id ?: 0)
             putString(USER_DESCRIPTION , user.bio)
             putStringSet(RANK_TABLE, emptySet())
             putBoolean(IS_PUBLIC, true)
    }
    suspend fun saveCodeLocal(code :String)= modify{
            putString(USER_CODE , code)
            putInt(USER_COUNTER  , 0)
    }
    suspend fun incrementCounter() = modify{
            putInt(USER_COUNTER , preference.getInt(USER_COUNTER , 0)+1)
    }
    suspend fun getCounter():Int = get{
       getInt(USER_COUNTER , 0)
    }

    suspend fun isCodeCorrect(code:String):Boolean = get{
        getString(USER_CODE, "").equals(code)
    }

    suspend fun removeCode()= modify {
        remove(USER_CODE)
        remove(USER_COUNTER)

    }
    suspend fun saveUserLogged(info:LoginResult) = modify{
               putString(USER_EMAIL, info.email)
               putString(USER_IMAGEURL, info.picture)
               putString(USER_NAME, info.fname)
               putString(USER_PRENAME, info.lname)
               putBoolean(USER_CONNECTED, true)
               putInt(USER_POINT, info.point ?: 0)
               putInt(USER_RANK, info.currentrank ?: 0)
               putInt(NQSOLVED, info.nbsolved ?: 0)
               putString(USER_DATE, info.date)
               putInt(USER_ID, info.id ?: 0)
               putInt(USER_YEAR, info.year ?: 0)
               putBoolean(IS_PUBLIC, info.ispublic == 1)
               putString(USER_DESCRIPTION , info.bio)
               putStringSet(RANK_TABLE, info.ranks?.map { it.toString() }?.toSet())
    }

    suspend fun isUserConnected():Boolean = get{
        getBoolean(USER_CONNECTED , false)
    }
}
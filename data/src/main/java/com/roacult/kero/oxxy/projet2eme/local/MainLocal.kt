package com.roacult.kero.oxxy.projet2eme.local

import android.content.SharedPreferences
import android.util.Log
import com.roacult.kero.oxxy.domain.modules.User
import com.roacult.kero.oxxy.projet2eme.network.entities.Request
import com.roacult.kero.oxxy.projet2eme.utils.*
import javax.inject.Inject

open class MainLocal @Inject constructor(preferences: SharedPreferences):LocalStorage(preferences){
    suspend fun getChallengeRequest():Request = get{
        Request( year =  preference.getInt(USER_YEAR, 0) ,
           id = preference.getInt(
           USER_ID , 0))
    }

    suspend fun getUser() = get {

        User(getInt(USER_ID  , 0) ,getString(USER_EMAIL, "") , getString(USER_NAME , "") ,
            getString(USER_PRENAME , "") , getBoolean(IS_PUBLIC , true) , getString(USER_IMAGEURL ,""),
            getInt(USER_YEAR , 0), getString(USER_DATE, "") , getInt(NQSOLVED , 0), getInt(USER_POINT , 0)
        ,
        getInt(USER_RANK , 0) ,  getStringSet(RANK_TABLE, emptySet()).map {it.toInt()} ,getString(
                USER_DESCRIPTION,"")
        )

    }
    suspend fun updateUserData(lname:String , fname:String , ispublic:Boolean , imageUrl:String? )= modify{
            putString(USER_NAME , fname)
            putString(USER_PRENAME , lname)
            if(imageUrl !=null) putString(USER_IMAGEURL , imageUrl)
            putBoolean(IS_PUBLIC, ispublic)
    }

     fun logOut()= preference.edit().apply(){
            remove(USER_ID)
            remove(USER_YEAR)
            remove(USER_EMAIL)
            remove(USER_DATE)
            remove(USER_COUNTER)
            remove(USER_RANK)
            remove(USER_CODE)
            remove(USER_POINT)
            remove(USER_PRENAME)
            remove(USER_NAME)
            remove(USER_IMAGEURL)
            remove(USER_CONNECTED)
            remove(IS_PUBLIC)
            remove(RANK_TABLE)
            remove(USER_DESCRIPTION)
            commit()
    }
    suspend fun getMail():String  = get{ getString(USER_EMAIL , "")}
     fun remove() {
      preference.edit().remove(CHALLENGE_NSOLVED).apply()
    }

    fun checkNumber(it: Int): Boolean
    = preference.getInt(CHALLENGE_NSOLVED , 0) !=it


    fun save(it: Int?) {
    preference.edit().putInt(CHALLENGE_NSOLVED, it?:0).commit()
    }

    fun getUserId():Int  = preference.getInt(USER_ID , 0)
    suspend fun addPointToUser(points: Long) {
        val currentPoint =get{getInt(USER_POINT , 0)}
        modify {  putInt(USER_POINT , ( currentPoint+points).toInt())}

    }
}
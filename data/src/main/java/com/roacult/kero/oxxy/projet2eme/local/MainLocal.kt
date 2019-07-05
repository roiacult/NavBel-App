package com.roacult.kero.oxxy.projet2eme.local

import android.content.SharedPreferences
import com.roacult.kero.oxxy.domain.modules.User
import com.roacult.kero.oxxy.projet2eme.network.entities.Request
import com.roacult.kero.oxxy.projet2eme.utils.*
import javax.inject.Inject

open class MainLocal @Inject constructor( private val preferences: SharedPreferences){
    fun getChallengeRequest():Request{
       return Request( year =  preferences.getInt(USER_YEAR, 0) ,
           id = preferences.getInt(
           USER_ID , 0))
    }
    fun getUser() = preferences.run {
        User(getInt(USER_ID  , 0) ,getString(USER_EMAIL, "") , getString(USER_NAME , "") ,
            getString(USER_PRENAME , "") , getBoolean(IS_PUBLIC , true) , getString(USER_IMAGEURL ,""),
            getInt(USER_YEAR , 0), getString(USER_DATE, "") , getInt(NQSOLVED , 0), getInt(USER_POINT , 0)
        ,
        getInt(USER_RANK , 0) , arrayListOf(20,22 , 33 , 14 )
        )
    }
    fun updateUserData(lname:String , fname:String , ispublic:Boolean , imageUrl:String? ){
        preferences.edit().apply{
            putString(USER_NAME , fname)
            putString(USER_PRENAME , lname)
            putBoolean(IS_PUBLIC, ispublic)
            if(imageUrl !=null) putString(USER_IMAGEURL , imageUrl)
            commit()
        }
    }

    fun logOut(){
        preferences.edit().apply{
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
        }.apply()
    }
    fun getMail():String  = preferences.getString(USER_EMAIL , "")
    fun remove() {
      preferences.edit().remove(CHALLENGE_NSOLVED)
    }

    fun checkNumber(it: Int): Boolean
    = preferences.getInt(CHALLENGE_NSOLVED , 0) !=it


    fun save(it: Int?) {
    preferences.edit().putInt(CHALLENGE_NSOLVED, it?:0).commit()
    }
    fun getUserId():Int  = preferences.getInt(USER_ID , 0)
    fun addPointToUser(points: Long) {
      preferences.edit().putInt(USER_POINT , (preferences.getInt(USER_POINT , 0)+points).toInt()).apply()
    }
}
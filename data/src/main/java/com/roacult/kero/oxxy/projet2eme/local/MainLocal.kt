package com.roacult.kero.oxxy.projet2eme.local

import android.content.SharedPreferences
import com.roacult.kero.oxxy.projet2eme.network.entities.Request
import com.roacult.kero.oxxy.projet2eme.utils.*
import javax.inject.Inject

class MainLocal @Inject constructor( private val preferences: SharedPreferences){
    fun getChallengeRequest():Request{
       return Request( year =  preferences.getInt(USER_YEAR, 0) ,
           id = preferences.getLong(
           USER_ID , 0))
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
        }
    }
    fun getMail():String  = preferences.getString(USER_EMAIL , "")
    fun remove() {
      preferences.edit().remove(CHALLENGE_NSOLVED)
    }

    fun checkNumber(it: Int): Boolean
    = preferences.getInt(CHALLENGE_NSOLVED , 0) !=it


    fun save(it: Int?) {
    preferences.edit().putInt(CHALLENGE_NSOLVED, it?:0)
    }
}
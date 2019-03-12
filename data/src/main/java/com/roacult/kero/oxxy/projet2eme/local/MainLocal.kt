package com.roacult.kero.oxxy.projet2eme.local

import android.content.SharedPreferences
import com.roacult.kero.oxxy.projet2eme.network.entities.Request
import com.roacult.kero.oxxy.projet2eme.utils.USER_EMAIL
import com.roacult.kero.oxxy.projet2eme.utils.USER_ID
import com.roacult.kero.oxxy.projet2eme.utils.USER_YEAR
import javax.inject.Inject

class MainLocal @Inject constructor( private val preferences: SharedPreferences){
    fun getChallengeRequest():Request{
       return Request( year =  preferences.getInt(USER_YEAR, 0) ,  email = preferences.getString(USER_EMAIL , "") ,
           userId = preferences.getLong(
           USER_ID , 0))
    }
}
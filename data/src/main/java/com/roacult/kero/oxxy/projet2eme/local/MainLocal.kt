package com.roacult.kero.oxxy.projet2eme.local

import android.content.SharedPreferences
import com.roacult.kero.oxxy.projet2eme.utils.USER_YEAR
import javax.inject.Inject

class MainLocal @Inject constructor( private val preferences: SharedPreferences){
    fun getYear():Int{
       return  preferences.getInt(USER_YEAR, 0)
    }
}
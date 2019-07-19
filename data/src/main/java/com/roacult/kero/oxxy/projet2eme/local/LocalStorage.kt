package com.roacult.kero.oxxy.projet2eme.local

import android.content.SharedPreferences
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

open class LocalStorage(val preference: SharedPreferences) {
    /**
     * a suspend function to modify the shared preferences
     */
    suspend  fun modify(fnc:SharedPreferences.Editor.()->SharedPreferences.Editor)= coroutineScope {
        launch { preference.edit().fnc().commit()  }.join()
    }

    /**
     * a suspend function to get from the shared preference
     */
    suspend   fun <T> get(fnc: SharedPreferences.() -> T):T = coroutineScope {
        async { preference.fnc() }.await()
    }
}
package com.roacult.kero.oxxy.projet2eme.utils.appinitializer

import com.facebook.stetho.Stetho
import com.roacult.kero.oxxy.projet2eme.AndroidApplication
import com.roacult.kero.oxxy.projet2eme.base.AppInitializer
import javax.inject.Inject

class StethoInitializer @Inject constructor() :AppInitializer {
    override fun init(application: AndroidApplication) {
        Stetho.initializeWithDefaults(application)

    }
}
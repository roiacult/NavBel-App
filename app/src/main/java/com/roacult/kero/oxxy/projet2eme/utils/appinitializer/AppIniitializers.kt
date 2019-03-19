package com.roacult.kero.oxxy.projet2eme.utils.appinitializer

import com.roacult.kero.oxxy.projet2eme.AndroidApplication
import com.roacult.kero.oxxy.projet2eme.base.AppInitializer
import javax.inject.Inject

class AppIniitializers @Inject constructor(
    private val initializers: Set<@JvmSuppressWildcards AppInitializer>
) {
    fun init(application: AndroidApplication) {
        initializers.forEach {
            it.init(application)
        }
    }
}
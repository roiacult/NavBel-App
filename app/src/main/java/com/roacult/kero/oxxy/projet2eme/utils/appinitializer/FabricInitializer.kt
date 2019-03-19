package com.roacult.kero.oxxy.projet2eme.utils.appinitializer

import com.crashlytics.android.Crashlytics
import com.roacult.kero.oxxy.projet2eme.AndroidApplication
import com.roacult.kero.oxxy.projet2eme.base.AppInitializer
import io.fabric.sdk.android.Fabric
import javax.inject.Inject

class FabricInitializer @Inject constructor():AppInitializer {
    override fun init(application: AndroidApplication) {
        Fabric.with(application  , Crashlytics())

    }
}
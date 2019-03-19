package com.roacult.kero.oxxy.projet2eme.utils.appinitializer

import com.instabug.library.Instabug
import com.instabug.library.invocation.InstabugInvocationEvent
import com.roacult.kero.oxxy.projet2eme.AndroidApplication
import com.roacult.kero.oxxy.projet2eme.base.AppInitializer
import javax.inject.Inject

class InstaBugInitializer @Inject constructor():AppInitializer{
    override fun init(application: AndroidApplication) {
        Instabug.Builder(application, "bd7ff81db1b61cb2ec5f4008c4b2ad9b")
            .setInvocationEvents(InstabugInvocationEvent.SHAKE, InstabugInvocationEvent.SCREENSHOT)
            .build()
    }
}
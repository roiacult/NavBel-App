package com.roacult.kero.oxxy.projet2eme

import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.roacult.kero.oxxy.projet2eme.di.component.DaggerAppComponent
import io.fabric.sdk.android.Fabric
import com.instabug.library.invocation.InstabugInvocationEvent
import com.instabug.library.Instabug



class AndroidApplication :DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        Stetho.initializeWithDefaults(this)
        Fabric.with(this  , Crashlytics())
        Instabug.Builder(this, "bd7ff81db1b61cb2ec5f4008c4b2ad9b")
            .setInvocationEvents(InstabugInvocationEvent.SHAKE, InstabugInvocationEvent.SCREENSHOT)
            .build()
        return  DaggerAppComponent.builder().create(this)
    }
}
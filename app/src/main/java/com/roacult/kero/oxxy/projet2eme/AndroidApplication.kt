package com.roacult.kero.oxxy.projet2eme

import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.roacult.kero.oxxy.projet2eme.di.component.DaggerAppComponent
import io.fabric.sdk.android.Fabric

class AndroidApplication :DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        Stetho.initializeWithDefaults(this)
        Fabric.with(this  , Crashlytics())
        return  DaggerAppComponent.builder().create(this)
    }
}
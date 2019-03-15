package com.roacult.kero.oxxy.projet2eme

import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.roacult.kero.oxxy.projet2eme.di.component.DaggerAppComponent

class AndroidApplication :DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        Stetho.initializeWithDefaults(this)
        return  DaggerAppComponent.builder().create(this)
    }
}
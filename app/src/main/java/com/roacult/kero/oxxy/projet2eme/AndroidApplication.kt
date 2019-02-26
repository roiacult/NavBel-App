package com.roacult.kero.oxxy.projet2eme

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.roacult.kero.oxxy.projet2eme.di.Component.DaggerAppComponent

class AndroidApplication :DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return  DaggerAppComponent.builder().create(this)
    }
}
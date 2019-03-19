package com.roacult.kero.oxxy.projet2eme

import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.roacult.kero.oxxy.projet2eme.di.component.DaggerAppComponent
import io.fabric.sdk.android.Fabric
import com.instabug.library.invocation.InstabugInvocationEvent
import com.instabug.library.Instabug
import com.roacult.kero.oxxy.projet2eme.utils.appinitializer.AppIniitializers
import javax.inject.Inject


class AndroidApplication :DaggerApplication() {
    @Inject
    lateinit var initializers: AppIniitializers
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return  DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        initializers.init(this)
    }
}
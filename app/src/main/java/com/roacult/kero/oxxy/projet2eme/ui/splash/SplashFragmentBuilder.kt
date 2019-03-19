package com.roacult.kero.oxxy.projet2eme.ui.splash

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SplashFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun provideSplashFragment() : SplashFragment
}
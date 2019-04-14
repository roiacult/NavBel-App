package com.roacult.kero.oxxy.projet2eme.ui

import com.roacult.kero.oxxy.projet2eme.ui.main.MainActivity
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.MainFragmentBuilder
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.RegistrationFragmentBuilder
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.RegistrationActivity
import com.roacult.kero.oxxy.projet2eme.ui.setting.SettingActivity
import com.roacult.kero.oxxy.projet2eme.ui.setting.SettingBuilder
import com.roacult.kero.oxxy.projet2eme.ui.splash.SplashActivity
import com.roacult.kero.oxxy.projet2eme.ui.splash.SplashFragment
import com.roacult.kero.oxxy.projet2eme.ui.splash.SplashFragmentBuilder
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.StartChalengeActivity
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.StartChalengeFragmentBuilder
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [SplashFragmentBuilder::class])
    abstract fun provideSplash() : SplashActivity

    @ContributesAndroidInjector(modules = [RegistrationFragmentBuilder::class])
    abstract fun provideRegistration() : RegistrationActivity

    @ContributesAndroidInjector(modules = [MainFragmentBuilder::class])
    abstract fun provideMain() : MainActivity

    @ContributesAndroidInjector(modules = [StartChalengeFragmentBuilder::class])
    abstract fun provideStartChalenge() : StartChalengeActivity

    @ContributesAndroidInjector(modules = [SettingBuilder::class])
    abstract  fun provideSettings() : SettingActivity

}
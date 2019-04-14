package com.roacult.kero.oxxy.projet2eme.ui.setting

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SettingBuilder {

    @ContributesAndroidInjector
    abstract fun provideSettingFragment() :SettingFragment
}
package com.roacult.kero.oxxy.projet2eme.ui.creatpost

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CreatPostBuilder {

    @ContributesAndroidInjector
    abstract fun provideCreatPostFragment() : CreatPostFragment
}
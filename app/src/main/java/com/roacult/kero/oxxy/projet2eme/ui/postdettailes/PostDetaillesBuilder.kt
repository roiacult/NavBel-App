package com.roacult.kero.oxxy.projet2eme.ui.postdettailes

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PostDetaillesBuilder {

    @ContributesAndroidInjector
    abstract fun providePostDetaillesFragment() : PostDetaillesFragment
}
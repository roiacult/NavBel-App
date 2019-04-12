package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge

import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.resourefragment.ResourceFragment
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.chalengefragment.ChalengeFragment
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.resultfragments.ResultFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class StartChalengeFragmentBuilder{

    @ContributesAndroidInjector
    abstract fun provideFirstFragmeent() : ResourceFragment

    @ContributesAndroidInjector
    abstract fun provideSecondFragmeent() : ChalengeFragment

    @ContributesAndroidInjector
    abstract fun provideResulFragment() : ResultFragment
}
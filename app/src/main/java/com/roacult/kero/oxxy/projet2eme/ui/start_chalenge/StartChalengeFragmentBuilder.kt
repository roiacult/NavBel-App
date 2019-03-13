package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge

import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.first_fragment.FirstFragment
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.second_fragment.SecondFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class StartChalengeFragmentBuilder{

    @ContributesAndroidInjector
    abstract fun provideFirstFragmeent() : FirstFragment

    @ContributesAndroidInjector
    abstract fun provideSecondFragmeent() : SecondFragment
}
package com.roacult.kero.oxxy.projet2eme.ui.solvedchallenge

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SolvedChallengeBuilder {

    @ContributesAndroidInjector
    abstract fun provideSolvedChallengeFragment() : SolvedChallengeFragment
}
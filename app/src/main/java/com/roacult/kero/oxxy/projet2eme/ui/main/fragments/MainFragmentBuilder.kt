package com.roacult.kero.oxxy.projet2eme.ui.main.fragments

import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.award_fragment.AwardFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.award_fragment.getgift.GetGiftFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.chalenge_fragment.ChalengeFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.forume_fragment.ForumeFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.profile_fragment.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun provideChalngeFragment() : ChalengeFragment

    @ContributesAndroidInjector
    abstract fun provideForumeFragment() : ForumeFragment

    @ContributesAndroidInjector
    abstract fun provideAwardFragment() : AwardFragment

    @ContributesAndroidInjector
    abstract fun provideProfileFragment() : ProfileFragment

    @ContributesAndroidInjector
    abstract fun provideGiftFragment() : GetGiftFragment
}
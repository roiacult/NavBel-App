package com.roacult.kero.oxxy.projet2eme.ui.registration_feature

import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_saveinfo.SaveInfoFragment
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_signin_login.RegistrationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RegistrationFragmentBuilder {

    @ContributesAndroidInjector
    abstract fun provideRegistartionFragment() : RegistrationFragment

    @ContributesAndroidInjector
    abstract fun provideSaveInfoFragment() : SaveInfoFragment
}
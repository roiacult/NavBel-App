package com.roacult.kero.oxxy.projet2eme.ui

import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.RegistrationFragmentBuilder
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.RegistrationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [RegistrationFragmentBuilder::class])
    abstract fun provideRegistration() : RegistrationActivity


}
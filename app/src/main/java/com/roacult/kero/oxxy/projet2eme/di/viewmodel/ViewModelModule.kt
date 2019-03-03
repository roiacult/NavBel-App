package com.roacult.kero.oxxy.projet2eme.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_saveinfo.SaveInfoViewModel
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_signin_login.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    internal abstract fun registrationViewModel(viewModel : RegistrationViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SaveInfoViewModel::class)
    internal abstract fun saveInfoViewModel(viewModel : SaveInfoViewModel) : ViewModel

}
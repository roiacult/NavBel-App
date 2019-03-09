package com.roacult.kero.oxxy.projet2eme.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.chalenge_fragment.ChalengeViewModel
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_saveinfo.SaveInfoViewModel
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_signin_login.RegistrationViewModel
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.reset_password.RestPasswordViewModel
import com.roacult.kero.oxxy.projet2eme.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun splashViewModel(viewModel : SplashViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    internal abstract fun registrationViewModel(viewModel : RegistrationViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SaveInfoViewModel::class)
    internal abstract fun saveInfoViewModel(viewModel : SaveInfoViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RestPasswordViewModel::class)
    internal abstract fun resetPasswordViewModel(viewModel : RestPasswordViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChalengeViewModel::class)
    internal abstract fun mainChalengesViewModel(viewModel : ChalengeViewModel) : ViewModel
}
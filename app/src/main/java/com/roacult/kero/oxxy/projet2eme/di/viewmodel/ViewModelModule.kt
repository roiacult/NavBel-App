package com.roacult.kero.oxxy.projet2eme.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.roacult.kero.oxxy.projet2eme.ui.creatpost.CreatPostViewModel
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.award_fragment.AwardViewModel
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.award_fragment.getgift.GetGiftViewModel
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.chalenge_fragment.ChalengeViewModel
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.forume_fragment.ForumViewModel
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.profile_fragment.ProfileViewModel
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_saveinfo.SaveInfoViewModel
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_signin_login.RegistrationViewModel
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.reset_password.RestPasswordViewModel
import com.roacult.kero.oxxy.projet2eme.ui.setting.SettingViewModel
import com.roacult.kero.oxxy.projet2eme.ui.splash.SplashViewModel
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.StartChelngeViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(StartChelngeViewModel::class)
    internal abstract fun startChalengesViewModel(viewModel : StartChelngeViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun provideProfileViewMode(viewModel : ProfileViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    internal abstract fun provideSettingViewModel(viewModel:SettingViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AwardViewModel::class)
    internal abstract fun provideAwardViewModel(viewModel:AwardViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetGiftViewModel::class)
    internal abstract fun provideGetGiftViewModel(viewModel:GetGiftViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ForumViewModel::class)
    internal abstract fun provideForumViewModel(viewModel : ForumViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreatPostViewModel::class)
    internal abstract fun provideCreatPosstViewModel(viewModel : CreatPostViewModel): ViewModel
}
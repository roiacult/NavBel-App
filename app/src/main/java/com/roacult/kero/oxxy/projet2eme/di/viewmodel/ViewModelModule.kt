package com.roacult.kero.oxxy.projet2eme.di.viewmodel

import androidx.lifecycle.ViewModelProvider
import com.roacult.kero.oxxy.projet2eme.di.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module



@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
//


}
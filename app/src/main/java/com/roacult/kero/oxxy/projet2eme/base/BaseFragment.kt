package com.roacult.kero.oxxy.projet2eme.base

import android.arch.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

open class BaseFragment :DaggerFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
     interface BaseCallBack
}
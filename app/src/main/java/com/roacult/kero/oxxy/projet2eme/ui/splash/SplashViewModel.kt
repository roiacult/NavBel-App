package com.roacult.kero.oxxy.projet2eme.ui.splash

import android.util.Log
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.UserStateUseCase
import com.roacult.kero.oxxy.domain.interactors.launchInteractor
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Loading

import javax.inject.Inject

class SplashViewModel @Inject constructor(userState: UserStateUseCase) : BaseViewModel<SplashState>(SplashState(null)) {

    init {
        setState { copy(Loading()) }
        scope.launchInteractor(userState, None()){
            Log.e("errr", it.toString()+"helow")
            setState { copy(Success(it)) }
        }
    }
}
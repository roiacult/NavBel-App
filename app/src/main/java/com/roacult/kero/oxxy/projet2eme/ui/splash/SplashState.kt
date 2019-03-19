package com.roacult.kero.oxxy.projet2eme.ui.splash

import com.roacult.kero.oxxy.projet2eme.base.State
import com.roacult.kero.oxxy.projet2eme.utils.Async


data class SplashState( val userStateOp : Async<Boolean>?) : State


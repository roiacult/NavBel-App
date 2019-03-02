package com.roacult.kero.oxxy.projet2eme.ui.registration_feature

import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.projet2eme.base.State
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Event

data class RegistrationState(val viewState : Int ,val signInOperation : Event<Async<None>>?,val logInOperation : Event<Async<None>>?) : State

const val REGISTRATION_STATE_DEFAULT= 0
const val REGISTRATION_STATE_SIGNIN= 1
const val REGISTRATION_STATE_LOGIN= 2

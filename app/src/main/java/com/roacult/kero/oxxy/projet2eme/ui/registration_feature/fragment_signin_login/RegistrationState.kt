package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_signin_login

import com.roacult.kero.oxxy.domain.interactors.MailResult
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.projet2eme.base.State
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Event

data class RegistrationState(val viewState : Int ,val signInOperation : Event<Async<MailResult>>?,val logInOperation : Event<Async<None>>?) : State

const val REGISTRATION_STATE_DEFAULT= 0
const val REGISTRATION_STATE_SIGNIN= 1
const val REGISTRATION_STATE_LOGIN= 2

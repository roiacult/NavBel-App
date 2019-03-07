package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.reset_password

import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.projet2eme.base.State
import com.roacult.kero.oxxy.projet2eme.utils.Event
import com.roacult.kero.oxxy.projet2eme.utils.Async

data class RestPasswordState (val viewState :Int, val  sendCodeToEmailOp : Event< Async<None>>?,
                         val confirmEmailOp : Event<Async<None>>?,
                         val resendCodeToEmailOp : Event<Async<None>>?,
                              val changinPassOp :Event<Async<None>>?) : State

const val REST_PASS_STATE_SEND = 0
const val REST_PASS_STATE_CONFIRM = 1
const val REST_PASS_STATE_CHANGE = 1
package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.reset_password

import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import javax.inject.Inject

class RestPasswordViewModel @Inject constructor() : BaseViewModel<RestPasswordState>(RestPasswordState(REST_PASS_STATE_SEND,
    null,
    null,
    null)) , ResetPasswordFragment.CallbackFromViewModel {

    var lastSendTime = -1

    override fun setView(state: Int) {
        setState { copy(viewState = state) }
    }

    override fun sendEmail(email: String) {

    }

    override fun confirmCode(code: String) {

    }
}
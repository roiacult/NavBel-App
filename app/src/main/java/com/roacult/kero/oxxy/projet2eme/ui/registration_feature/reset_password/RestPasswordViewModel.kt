package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.reset_password

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.interactors.*
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.Event
import javax.inject.Inject

class RestPasswordViewModel @Inject constructor(val sendigCode: RestPassSendigCode,
                                                val confirmEmail: ConfirmEmail,
                                                val resendEmail: ResendConfirmationCode,
                                                val changePassword : ResetPassword) : BaseViewModel<RestPasswordState>(RestPasswordState(REST_PASS_STATE_SEND,
    null,
    null,
    null,
    null)) , ResetPasswordFragment.CallbackFromViewModel {

    var lastSendTime = -1L
    var email =""

    override fun setView(state: Int) {
        setState { copy(viewState = state) }
    }
//////////////// sending email op /////////////////////////////////////////////////
    override fun sendEmail(email: String) {
        setState { copy(sendCodeToEmailOp = Event( Loading() ) ) }
        scope.launchInteractor(sendigCode,email){
            it.either(::handleSendingEmailFaile,::handelSendingEmailSuccess)
        }
    }

    private fun handelSendingEmailSuccess(none: None) {
        lastSendTime = System.currentTimeMillis()
        setState { copy(sendCodeToEmailOp = Event(Success(none))) }
    }

    private fun handleSendingEmailFaile(resetPasswordFailure: Failure.SendCodeResetPassword) {
        setState{copy(sendCodeToEmailOp = Event(Fail(resetPasswordFailure)))}
    }
///////////////////////confirm code op //////////////////////////////////////////
    override fun confirmCode(code: String) {
        setState { copy(confirmEmailOp = Event(Loading())) }
        scope.launchInteractor(confirmEmail,code){
            it.either(::handleConfirmFaile,::handleConfirmSuccess)
        }
    }

    private fun handleConfirmSuccess(none: None) {
        setState{copy(confirmEmailOp = Event(Success(none)))}
    }

    private fun handleConfirmFaile(confirmEmailFaillure: Failure.ConfirmEmailFaillure) {
        setState { copy(confirmEmailOp = Event(Fail(confirmEmailFaillure))) }
    }

/////////////////////////////// resend code op ////////////////////////////////////
    override fun resendEmail() {
        setState { copy(resendCodeToEmailOp = Event(Loading())) }
        scope.launchInteractor(resendEmail,email){
            it.either(::handleResendCodeFaillure,::handleRsendCodeSuccess)
        }
    }

    private fun handleRsendCodeSuccess(none: None) {
        lastSendTime = System.currentTimeMillis()
        setState{copy(resendCodeToEmailOp = Event(Success(none)))}
    }

    private fun handleResendCodeFaillure(resendConfirmationFailure: Failure.ResendConfirmationFailure) {
        setState{copy(resendCodeToEmailOp = Event(Fail(resendConfirmationFailure)))}
    }

////////////////////////////////// change password op //////////////////////////////////////
    override fun changePassword(password: String) {
        setState { copy(changinPassOp = Event(Loading())) }
        scope.launchInteractor(changePassword, ResetPasswordParams(email,password)){
            it.either(::handleChangingPassFail,::handleChangingPassSuccess)
        }
    }

    private fun handleChangingPassSuccess(none: None) {
        setState{copy(changinPassOp = Event(Success(none)))}
    }

    private fun handleChangingPassFail(resetPasswordFailure: Failure.ResetPasswordFailure) {
        setState { copy(changinPassOp = Event(Fail(resetPasswordFailure))) }
    }
}
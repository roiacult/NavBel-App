package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_signin_login

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.interactors.*
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.utils.Event
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import javax.inject.Inject

    /**
     * constant time for resending email 3 min
     * user must wait
     * @param RESEND_EMAIL_TIME miliseconds
     * to performe resending confirmation email
     *
     * */
    const val RESEND_EMAIL_TIME = 3*60*1000L

class RegistrationViewModel @Inject constructor(val signInOp: SignInUseCase,val confirmationOp : ConfirmEmail, val loginOp: Login,val resendOp : ResendConfirmationCode) :
    BaseViewModel<RegistrationState>(
        RegistrationState(
            REGISTRATION_STATE_DEFAULT,
            null,
            null,
            null,
            null
        )
    ) ,
    RegistrationFragment.CallbackFromViewModel {

    var email :String = ""
    var name :String =""
    var lastName: String = ""
    var year : Int = 0
    var lastResendTime = -1L

    override fun setView(state: Int) {
        setState { copy(viewState = state) }
    }

    override fun login(email: String, password: String) {
        setState { copy(logInOperation = Event(Loading())) }
        scope.launchInteractor(loginOp, LoginParam(email,password)){
            it.either(::handleLoginFaillure,::handleLoginSuccess)
        }
    }

    private fun handleLoginSuccess(none: None) {
        setState{ copy(logInOperation = Event(Success(none)))}
    }

    private fun handleLoginFaillure(loginFaillure: Failure.LoginFaillure) {
        setState { copy(logInOperation = Event(Fail(loginFaillure))) }
    }

    override fun signIn(email: String) {
        setState { copy(signInOperation = Event( Loading())) }
        scope.launchInteractor(signInOp,email){it.either(::handleSignInFaill,::handleSignInSuccess)}
    }

    private fun handleSignInSuccess(mailResult: MailResult) {
        setState { copy(signInOperation = Event(Success(mailResult))) }
    }

    private fun handleSignInFaill(signInFaillure: Failure.SignInFaillure) {
        setState { copy(signInOperation = Event(Fail(signInFaillure))) }
    }

    override fun setUserInfo(name: String, lastName: String, year: Int,email :String) {
        this.name = name
        this.lastName =lastName
        this.year = year
        this.email = email
    }

    override fun confirmEmail(code : String){
        setState { copy(confirmatioOperation = Event(Loading())) }
        scope.launchInteractor(confirmationOp,code){
            it.either(::handelFaile,::handleSuccess)
        }
    }

    private fun handleSuccess(none: None) {
        setState { copy(confirmatioOperation = Event(Success(none))) }
    }

    private fun handelFaile(confirmEmailFaillure: Failure.ConfirmEmailFaillure) {
        setState { copy(confirmatioOperation = Event(Fail(confirmEmailFaillure))) }
    }
    override fun resendConfirmationCode(){
        setState{copy(resendOperation = Event(Loading()))}
        scope.launchInteractor(resendOp,email){
            it.either(::handleResendFaillure,::handleResendSuccess)
        }
    }

    private fun handleResendSuccess(none: None) {
        lastResendTime = System.currentTimeMillis()
        setState { copy(resendOperation = Event(Success(none))) }
    }

    private fun handleResendFaillure(failure: Failure) {
        setState{copy(resendOperation = Event(Fail(failure)))}
    }
}
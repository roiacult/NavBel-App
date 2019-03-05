package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_signin_login

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.interactors.*
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.utils.Event
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(val signInOp: SignInUseCase,val confirmationOp : ConfirmEmail, val loginOp: Login) :
    BaseViewModel<RegistrationState>(
        RegistrationState(
            REGISTRATION_STATE_DEFAULT,
            null,
            null,
            null
        )
    ) ,
    RegistrationFragment.CallbackFromViewModel {

    var name :String =""
    var lastName: String = ""
    var year : Int = 0

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

    override fun setUserInfo(name: String, lastName: String, year: Int) {
        this.name = name
        this.lastName =lastName
        this.year = year
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
}
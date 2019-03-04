package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_signin_login

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.interactors.MailResult
import com.roacult.kero.oxxy.domain.interactors.SignInUseCase
import com.roacult.kero.oxxy.domain.interactors.launchInteractor
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.utils.Event
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(val signInOp: SignInUseCase) :
    BaseViewModel<RegistrationState>(
        RegistrationState(
            REGISTRATION_STATE_DEFAULT,
            null,
            null
        )
    ) ,
    RegistrationFragment.CallbackFromViewModel {


    override fun setView(state: Int) {
        setState { copy(viewState = state) }
    }

    override fun login(email: String, password: String) {
//        setState { copy(logInOperation = Event(Loading())) }
        //TODO launche login interactor
    }

    override fun signIn(email: String) {
        setState { copy(signInOperation = Event( Loading())) }
        scope.launchInteractor(signInOp,email){it.either(::handleSignInFaill,::handleSignInSuccess)}
    }

    private fun handleSignInSuccess(mailResult: MailResult) {
        setState { copy(signInOperation = Event(Success(mailResult))) }
    }

    private fun handleSignInFaill(signInFaillure: Failure.SignInFaillure) {
        setState { copy(logInOperation = Event(Fail(signInFaillure))) }
    }

    override fun confirmEmail(code : String){
//        launchInteractor
    }
}
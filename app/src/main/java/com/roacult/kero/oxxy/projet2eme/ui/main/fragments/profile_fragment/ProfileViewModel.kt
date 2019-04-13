package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.profile_fragment

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.interactors.GetUserInfo
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.launchInteractor
import com.roacult.kero.oxxy.domain.modules.User
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.base.State
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import javax.inject.Inject

class ProfileViewModel @Inject constructor(userInfo : GetUserInfo) :BaseViewModel<ProfileState>(ProfileState()){

    init {
        scope.launchInteractor(userInfo, None()){
            it.either(::handleFaile,::handleSuccess)
        }
    }

    private fun handleSuccess(user: User) {
        setState { copy(userInfo = Success(user)) }
    }

    private fun handleFaile(getUserInfoFailure: Failure.GetUserInfoFailure) {
        setState{copy(userInfo = Fail(getUserInfoFailure))}
    }

}

data class ProfileState(val userInfo : Async<User> = Loading()) :State
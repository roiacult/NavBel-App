package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_saveinfo

import android.util.Log
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.SaveInfoUseCase
import com.roacult.kero.oxxy.domain.interactors.UserInfo
import com.roacult.kero.oxxy.domain.interactors.launchInteractor
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.utils.Event
import javax.inject.Inject
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.Fail


class SaveInfoViewModel @Inject constructor(val saveInfoUseCase: SaveInfoUseCase): BaseViewModel<SaveInfoState>(SaveInfoState(null,null)),SaveInfoFragment.CallbackToViewModel {
    /*image null means user didn't pick image yet , event null means he didn't perform submit operation yet */

    var firstTime = true
    var year = 0
    var email = ""

    override fun setInfo(year: Int,email : String) {
        this.year = year
        this.email = email
        firstTime =false
    }

    override fun setImage(url : String){
        setState{copy(imageUrl = url)}
    }

    override fun submit(name: String, lastName: String ,password: String) {
        setState { copy(submitOperation = Event( Loading() ) ) }
        withState{
            Log.e("errr" , it.imageUrl)
            scope.launchInteractor(saveInfoUseCase, UserInfo(name,lastName,email, year,password,it.imageUrl)){
                it.either(::handleSubmiterror,::handleSuccessSubmit)
            }
        }

    }

    private fun handleSubmiterror(saveInfoFaillure: Failure.SaveInfoFaillure) {
        setState { copy(submitOperation = Event(Fail(saveInfoFaillure))) }
    }

    private fun handleSuccessSubmit(none: None) {
        setState { copy(submitOperation = Event(Success(none))) }
    }

    override fun isItFirstTime(): Boolean  = firstTime
}
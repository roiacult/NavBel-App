package com.roacult.kero.oxxy.projet2eme.ui.setting

import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.UpdateUserInfo
import com.roacult.kero.oxxy.domain.interactors.UpdateUserInfoParam
import com.roacult.kero.oxxy.domain.interactors.launchInteractor
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.base.State
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import javax.inject.Inject

class SettingViewModel @Inject constructor(val updateUserInfo: UpdateUserInfo)  : BaseViewModel<SettingState>(SettingState()){

    private var firestTime =true


    var newPic :String? = null

    var fName : String? = null
    var lName : String? = null
    var picture : String? = null
    var description : String? = null
    var public : Boolean = false

    fun saveInfo(fName :String,lName :String, picture :String?,descri : String? ,public :Boolean){
        if(firestTime) {
            this.fName = fName
            this.lName = lName
            this.picture = picture
            this.description = descri
            this.public = public
            firestTime = false
        }
    }

    fun save() {
        setState { copy(saveOp = Loading()) }
        scope.launchInteractor(updateUserInfo, UpdateUserInfoParam(fName!!,lName!!,newPic,public , null,description)){
            it.either({
                setState { copy(saveOp = Fail(it)) }
            },{
                setState{copy(saveOp = Success(it))}
            })
        }
    }
}

data class SettingState(val saveOp : Async<None>? = null) : State
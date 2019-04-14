package com.roacult.kero.oxxy.projet2eme.ui.setting

import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.base.State
import com.roacult.kero.oxxy.projet2eme.utils.Async
import javax.inject.Inject

class SettingViewModel @Inject constructor()  : BaseViewModel<SettingState>(SettingState()){

    var firestTime =true


    var fName : String? = null
    var lName : String? = null
    var picture : String? = null
    var public : Boolean = false

    fun saveInfo(fName :String,lName :String, picture :String?,public :Boolean){
        if(firestTime) {
            this.fName = fName
            this.lName = lName
            this.picture = picture
            this.public = public
            firestTime = false
        }
    }
}

data class SettingState(val saveOp : Async<None>? = null) : State
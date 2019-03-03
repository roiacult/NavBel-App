package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_saveinfo

import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import javax.inject.Inject

class SaveInfoViewModel @Inject constructor(): BaseViewModel<SaveInfoState>(SaveInfoState(null,null)),SaveInfoFragment.CallbackToViewModel {
    /*image null means user didn't pick image yet , event null means he didn't perform submit operation yet */

    var firstTime = true
    var fName =""
    var lName =""
    var year = 0

    override fun setInfo(fName: String, Lname: String, year: Int) {
        this.fName = fName
        this.lName = Lname
        this.year = year
    }

    override fun setImage(url : String){
        setState{copy(imageUrl = url)}
    }

    override fun isItFirstTime(): Boolean  = firstTime
}
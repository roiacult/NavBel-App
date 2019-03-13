package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge

import com.roacult.kero.oxxy.domain.interactors.GetChalengeDetaills
import com.roacult.kero.oxxy.domain.interactors.launchInteractor
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Event

class StartChelngeViewModel(private val chalengeDetaillesuseCase : GetChalengeDetaills) :
    BaseViewModel<StartChalengeState>(StartChalengeState(Event(STARTCHALENGE_FRAGMENT1),Loading())) {

    var firstTime = true

}
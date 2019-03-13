package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.chalenge_fragment

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.interactors.GetAllChallenges
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.launchInteractor
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Success
import javax.inject.Inject

class ChalengeViewModel @Inject constructor(val getChalenges : GetAllChallenges) : BaseViewModel<ChalengeState>(ChalengeState(null))
    , ChalengeFragment.CallbackFromViewModel{

    val modules = ArrayList<String>()

    init {
        setState { copy(getChalenges = Loading()) }
        requestData()
    }

    override fun requestData() {
        scope.launchInteractor(getChalenges, None()){
            it.either(::handleFailure,::handleSuccesss)
        }
    }

    private fun handleSuccesss(list: List<ChalengeGlobale>) {
        setState { copy(getChalenges = Success(list)) }
        val newList = list.map { it.module }
        modules.clear()
        modules.add("none")
        modules.addAll(newList)
    }

    private fun handleFailure(getAllChalengesFailure: Failure.GetAllChalengesFailure) {
        setState{copy(getChalenges = Fail(getAllChalengesFailure))}
    }

}
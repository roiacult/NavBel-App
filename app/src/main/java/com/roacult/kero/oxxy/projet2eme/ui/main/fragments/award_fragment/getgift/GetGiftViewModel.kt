package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.award_fragment.getgift

import com.roacult.kero.oxxy.domain.interactors.GetGift
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.launchInteractor
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.base.State
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import javax.inject.Inject

class GetGiftViewModel @Inject constructor(val getGift: GetGift) : BaseViewModel<GetGiftState>(GetGiftState()){


    fun getGift(awardId : Int){
        setState { copy(getGiftOp = Loading()) }
        scope.launchInteractor(getGift,awardId){
            it.either({
                setState{copy(getGiftOp = Fail(it))}
            },{
                setState{copy(getGiftOp = Success(it))}
            })
        }
    }
}

data class GetGiftState(val getGiftOp : Async<None>? = null) : State
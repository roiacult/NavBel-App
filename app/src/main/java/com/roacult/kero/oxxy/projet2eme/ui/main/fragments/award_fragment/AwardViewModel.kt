package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.award_fragment

import com.roacult.kero.oxxy.domain.interactors.GetAwards
import com.roacult.kero.oxxy.domain.interactors.GetUserInfo
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.launchInteractor
import com.roacult.kero.oxxy.domain.modules.Award
import com.roacult.kero.oxxy.domain.modules.User
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.base.State
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import javax.inject.Inject

class AwardViewModel @Inject constructor(val getAwards: GetAwards,val getUserInfo: GetUserInfo) : BaseViewModel<AwardState>(AwardState()){

    init { getRewards()}

    fun getRewards(){
        setState{copy(awards = Loading())}
        scope.launchInteractor(getAwards, None()){
            it.either({
                setState{copy(awards = Fail(it))}
            },{
                setState{copy(awards = Success(it))}
            })
        }
        scope.launchInteractor(getUserInfo,None()){
            it.either({
                setState { copy(userInfo = Fail(it)) }
            },{
                setState{copy(userInfo = Success(it))}
            })
        }
    }
}


data class AwardState(val awards : Async<List<Award>> = Loading(),val userInfo :  Async<User> = Loading()) : State
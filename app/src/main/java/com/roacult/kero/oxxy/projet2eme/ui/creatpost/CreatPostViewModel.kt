package com.roacult.kero.oxxy.projet2eme.ui.creatpost

import com.roacult.kero.oxxy.domain.interactors.GetUserInfo
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.Posting
import com.roacult.kero.oxxy.domain.interactors.launchInteractor
import com.roacult.kero.oxxy.domain.modules.Post
import com.roacult.kero.oxxy.domain.modules.User
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.base.State
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import javax.inject.Inject

class CreatPostViewModel @Inject constructor(
    private val sharePostInt : Posting ,
    userInfoInt : GetUserInfo
): BaseViewModel<CreatPostState> (CreatPostState()){

    var imageUri : String? = null
    var desc : String = ""

    init {
        scope.launchInteractor(userInfoInt,None()) {
            setState { copy( userInfoOp = Success(it) ) }
        }
    }

    fun sharePost() {
        setState { copy(shareOp = Loading()) }
        val user = (state.value!!.userInfoOp as Success).invoke()
        val year = if(user.year<3) user.year.toString() + " Cpi"
        else (user.year-1).toString() + " Cs"
        scope.launchInteractor(sharePostInt, Post(0,imageUri,desc,user.id.toLong(),user.fname+" ,"+user.lName,year,user.picture ?: "")){
            it.either({
                setState{copy(shareOp = Fail(it))}
            },{
                setState{copy(shareOp = Success(it))}
            })
        }
    }

}


data class CreatPostState (
    val shareOp : Async<None>? = null ,
    val userInfoOp : Async<User> = Loading()
) : State
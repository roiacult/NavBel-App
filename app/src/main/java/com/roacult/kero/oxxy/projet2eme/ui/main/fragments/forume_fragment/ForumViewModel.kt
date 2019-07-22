package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.forume_fragment

import com.roacult.kero.oxxy.domain.interactors.GetAllPosts
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.launchInteractor
import com.roacult.kero.oxxy.domain.modules.Post
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.base.State
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import javax.inject.Inject

class ForumViewModel @Inject constructor(
    getPostInt : GetAllPosts
) : BaseViewModel<ForumState>(ForumState()){

    init {
        scope.launchInteractor(getPostInt, None()){
            it.either({
                setState{copy(postsOp = Fail(it))}
            },{
                setState{copy(postsOp = Success(it))}
            })
        }
    }
}


data class ForumState(
    val postsOp : Async<List<Post>> = Loading()
) : State
package com.roacult.kero.oxxy.projet2eme.ui.postdettailes

import com.roacult.kero.oxxy.domain.interactors.CommentPost
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.PostDetailes
import com.roacult.kero.oxxy.domain.interactors.launchInteractor
import com.roacult.kero.oxxy.domain.modules.Comment
import com.roacult.kero.oxxy.domain.modules.Post
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.base.State
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Uninitialized
import javax.inject.Inject

class PostDetailesViewModel @Inject constructor(
    private val allPosts : PostDetailes ,
    private val postComment  : CommentPost
) : BaseViewModel<PosteDetailesState>(PosteDetailesState()){

    lateinit var post : Post
    private set

    fun saveData(p : Post){
        if(!::post.isInitialized){
            post = p
            refresh()
        }
    }

    fun refresh() {
        setState { copy(allCommentsOp = Loading()) }
        scope.launchInteractor(allPosts,post.postId){
            it.either({
                setState { copy(allCommentsOp = Fail(it)) }
            },{
                setState { copy(allCommentsOp = Success(it)) }
            })
        }
    }

    fun comment(comment: String) {
        setState { copy(commentOp = Loading()) }
        scope.launchInteractor(postComment,Pair(comment , post.postId)){
            it.either({
                setState { copy(commentOp = Fail(it)) }
            },{
                setState { copy(commentOp = Success(it)) }
            })
        }
    }

    fun deleteFailureInCommentOp() {
        setState{ copy(commentOp = null) }
    }

    fun deleteFailureInAllComment() {
        setState{ copy(allCommentsOp = Uninitialized) }
    }

}


data class PosteDetailesState(
    val allCommentsOp : Async<List<Comment>> = Loading(),
    val commentOp : Async<None>? = null
) : State
package com.roacult.kero.oxxy.projet2eme.ui.postdettailes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.modules.Comment
import com.roacult.kero.oxxy.domain.modules.Post
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.PostDetaillesBinding
import com.roacult.kero.oxxy.projet2eme.utils.*
import com.roacult.kero.oxxy.projet2eme.utils.extension.visible
import com.squareup.picasso.Picasso

class PostDetaillesFragment : BaseFragment() {

    companion object {
        fun getInstance(extra : Bundle) = PostDetaillesFragment().apply {
            this.arguments = extra
        }
    }

    private var needToRefresh = false
    private lateinit var binding : PostDetaillesBinding
    private val viewModel by lazy{ ViewModelProviders.of(this,viewModelFactory)[PostDetailesViewModel::class.java] }
    private val adapter = PostDetaillesAdapter()
    private val appBarStateListener =object : AppBarStateChangeListener(){
        override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
            when(state){
                State.COLLAPSED -> {
                    setToolbarTitle(false)
                }
                State.EXPANDED -> {
                    setToolbarTitle(true)
                }
                State.IDLE -> {
                    setToolbarTitle(true)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.post_detailles,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()

        viewModel.observe(this){
            handleComments(it.allCommentsOp)
            it.commentOp.apply{ handleCommentOp(this)}
        }

    }

    private fun handleCommentOp(async: Async<None>?) {
        when(async) {
            is Loading ->{
                commentOpLoading(true)
            }
            is Fail<*,*> -> {
                commentOpLoading(false)
                onError("some thing went wrong")
                viewModel.deleteFailureInCommentOp()
                needToRefresh = false
            }
            is Success -> {
                commentOpLoading(false)
                showMessage(R.string.coment_succ)
                binding.newComment.setText("")
                if(needToRefresh){
                    viewModel.refresh()
                    needToRefresh = false
                }
            }
        }
    }

    private fun commentOpLoading(show: Boolean) {
        binding.newComment.isClickable = !show
        binding.sendComment.isClickable = !show
    }

    private fun handleComments(allCommentsOp: Async<List<Comment>>) {
        when(allCommentsOp){
            is Loading -> {
                showCommentsLoading(true)
            }
            is Fail<*,*> -> {
                showCommentsLoading(false)
                onError("some thing went wrong")
                viewModel.deleteFailureInAllComment()
            }
            is Success -> {
                showCommentsLoading(false)
                val  list = allCommentsOp()
                if(list.isEmpty()) {
                    binding.comments.visible(false)
                    binding.emptyState.visible(true)
                }else {
                    binding.emptyState.visible(false)
                    adapter.addAll(list)
                }
            }
        }
    }

    private fun showCommentsLoading(show: Boolean) {
        binding.comments.visible(!show)
        binding.refresh.isRefreshing = show
    }

    private fun initViews() {
        saveDataToViewModel()

        if (viewModel.post.postImage?.isNotEmpty() == true ) {
            Picasso.get().load(viewModel.post.postImage).into(binding.postPic)
        }else {
            //TODO load post image holder
        }

        if(viewModel.post.userImage.isNotEmpty()) {
            Picasso.get().load(viewModel.post.userImage).into(binding.userPic)
        }
        binding.userName.text = viewModel.post.userName
        binding.userYear.text = viewModel.post.userYear
        binding.postDesc.text = viewModel.post.postDesc

        binding.sendComment.setOnClickListener{
            performCommentOp()
        }

        binding.refresh.setOnRefreshListener {
            viewModel.refresh()
        }

        binding.appbar.addOnOffsetChangedListener(appBarStateListener)
        binding.toolbar.title = ""
        binding.toolbar.setNavigationIcon(R.drawable.back)
        binding.toolbar.setNavigationOnClickListener {
            activity?.finish()
        }

        binding.comments.adapter = adapter
        binding.comments.layoutManager = LinearLayoutManager(context)
    }

    private fun performCommentOp() {
        val comment = binding.newComment.text.toString()

        if(comment.isEmpty()){
            onError(R.string.comment_error)
            return
        }

        needToRefresh = true
        viewModel.comment(comment)
    }

    private fun setToolbarTitle(show: Boolean) {
        binding.collapse.title = if(show) ""
        else getString(R.string.post_comment)
    }

    private fun saveDataToViewModel() {
        val postId = arguments!!.getLong(PostDetaillesActivity.POST_ID)
        val postImage =arguments!!.getString(PostDetaillesActivity.POST_IMAGE)
        val postDesc = arguments!!.getString(PostDetaillesActivity.POST_DESC)!!
        val userName = arguments!!.getString(PostDetaillesActivity.POST_USER_NAME)!!
        val userImage = arguments!!.getString(PostDetaillesActivity.POST_USER_IMAGE)!!
        val userYear = arguments!!.getString(PostDetaillesActivity.POST_USER_YEAR)!!
        val userID = arguments!!.getLong(PostDetaillesActivity.POST_USER_ID)
        viewModel.saveData( Post(postId,postImage,postDesc,userID,userName,userYear,userImage) )
    }
}

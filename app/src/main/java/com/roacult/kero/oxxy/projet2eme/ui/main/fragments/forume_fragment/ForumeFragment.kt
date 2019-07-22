package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.forume_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.roacult.kero.oxxy.domain.modules.Post
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.CallbackFromActivity
import com.roacult.kero.oxxy.projet2eme.databinding.MainForumBinding
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Success

class ForumeFragment  : BaseFragment(),CallbackFromActivity{
    companion object { fun getInstance() = ForumeFragment() }

    private lateinit var binding : MainForumBinding
    private val viewModel by lazy {ViewModelProviders.of(this,viewModelFactory)[ForumViewModel::class.java]}
    private var firstTime = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.main_forum,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()

        viewModel.observe(this){
            handlePostOp(it.postsOp)
        }
    }

    private fun handlePostOp(postsOp: Async<List<Post>>) {
        when(postsOp){
            is Loading -> {
                showLoading(true)

            }
            is Fail<*,*> -> {
                showLoading(false)
//                when(postsOp){
//                    TODO handle failures
//                }
                onError("some thing wrong happened")
            }
            is Success -> {
                showLoading(false)
                binding.viewPager.setCreativeViewPagerAdapter(ForumAdapter(postsOp()))
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.refresh.isRefreshing = show
        binding.viewPager.visibility = if(show) View.GONE else View.VISIBLE
    }

    private fun initViews() {
        binding.refresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    fun refresh() {
        if( !firstTime ) return
        firstTime = false
        viewModel.refresh()
    }

    override fun showHelp() {}

    override fun showFilter() {}
}
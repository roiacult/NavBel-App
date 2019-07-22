package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.forume_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.CallbackFromActivity
import com.roacult.kero.oxxy.projet2eme.databinding.MainForumBinding

class ForumeFragment  : BaseFragment(),CallbackFromActivity{
    companion object { fun getInstance() = ForumeFragment() }

    private lateinit var binding : MainForumBinding
    private val adapter = ForumAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_forum_post,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
    }

    private fun initViews() {

    }

    override fun showHelp() {}

    override fun showFilter() {}
}
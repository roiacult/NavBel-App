package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.award_fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.MainAwardPageBinding
import com.roacult.kero.oxxy.projet2eme.ui.main.CallbackFromActivity

class AwardFragment  : BaseFragment() , CallbackFromActivity {
    companion object { fun getInstance() = AwardFragment() }

    private lateinit var binding : MainAwardPageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MainAwardPageBinding.inflate(inflater,container,false)

        initViews()

        return binding.root
    }

    private fun initViews() {
        //TODO
        binding.indicator.setViewPager(binding.awards)
    }

    override fun showHelp(){

    }

    override fun showFilter() {}
}
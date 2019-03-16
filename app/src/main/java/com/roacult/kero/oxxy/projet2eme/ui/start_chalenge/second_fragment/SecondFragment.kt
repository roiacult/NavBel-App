package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.second_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.StartChelngeViewModel
import com.roacult.kero.oxxy.projet2eme.databinding.StartChalengeFragmnt2Binding
import com.roacult.kero.oxxy.projet2eme.utils.Success

class SecondFragment :BaseFragment() {

    private val viewModel : StartChelngeViewModel by lazy { ViewModelProviders.of(activity!!,viewModelFactory)[StartChelngeViewModel::class.java]}
    private lateinit var binding : StartChalengeFragmnt2Binding
    private lateinit var pagerAdapter: CardPagerAdapter

    companion object { fun getInstance() = SecondFragment() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.start_chalenge_fragmnt2,container,false)

        initialize()
        viewModel.observe(this){
            it.page.getContentIfNotHandled()?.apply { setUpPage(this) }
        }

        return binding.root
    }

    private fun initialize() {
        viewModel.withState {
            val detailles = (it.getChalengeDetailles as Success)()
            pagerAdapter= CardPagerAdapter()
            pagerAdapter.addAllCards(detailles.questions)
            binding.time.startTimer(detailles.time.toLong()){
                //TODO unsubsrive observer
            }
        }
    }

    private fun setUpPage(page: Int) {

    }
}
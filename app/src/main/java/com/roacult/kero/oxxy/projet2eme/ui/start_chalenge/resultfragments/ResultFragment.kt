package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.resultfragments

import android.app.Activity.RESULT_OK
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.ResultFragmentBinding
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.StartChelngeViewModel
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.R

class ResultFragment  : BaseFragment(){

    companion object {
        fun getInstance()  = ResultFragment()
    }

    private val viewModel by lazy { ViewModelProviders.of(activity!!,viewModelFactory)[StartChelngeViewModel::class.java]}
    private lateinit var binding : ResultFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding  = ResultFragmentBinding.inflate(inflater,container,false)

        initilze()

        return binding.root
    }

    private fun initilze() {
        val result = viewModel.submitionResult
        binding.close.setOnClickListener {
            activity?.finish()
        }
        binding.points.text = result.points.toString()
        if(result.points <= 0L) binding.points.setTextColor(Color.RED)
        binding.title.text = if(result.success) getString(R.string.congrat)
                             else getString(R.string.chalenge_fail)
        binding.animation.setAnimation(if(result.success) R.raw.success else R.raw.failed)
        binding.animation.playAnimation()
        val note = (result.points*100)/viewModel.chalengeGlobale.point
        var time : Int = 0
        viewModel.withState {
            time = viewModel.time.toInt()*100 /(it.getChalengeDetailles as Success).invoke().time
        }
        binding.noteProg.progress = note.toInt()
        binding.timeProg.progress = time
        binding.floatingActionButton.setOnClickListener{
            activity?.setResult(RESULT_OK)
            activity?.finish()
        }
    }
}
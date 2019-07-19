package com.roacult.kero.oxxy.projet2eme.ui.solvedchallenge

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.ResultFragmentBinding

class SolvedChallengeFragment : BaseFragment() {

    private lateinit var binding : ResultFragmentBinding

    companion object {

        fun getInstance(bundle : Bundle): SolvedChallengeFragment{
            return SolvedChallengeFragment().apply {
                arguments = bundle
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.result_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initilze()
    }

    private fun initilze() {
        binding.close.setOnClickListener {
            activity?.finish()
        }
        val points = arguments!!.getInt(SolvedChallengeActivity.POINT)
        val success = points >0
        binding.points.text = points.toString()
        if( !success ) binding.points.setTextColor(Color.RED)
        binding.title.text = if(success) getString(R.string.congrat)
        else getString(R.string.chalenge_fail)
        binding.animation.setAnimation(if(success) R.raw.success else R.raw.failed)
        binding.animation.playAnimation()
        val note = arguments!!.getFloat(SolvedChallengeActivity.POINT_PERCENT)
        val time =arguments!!.getFloat(SolvedChallengeActivity.TIME_PERCENT)
        binding.noteProg.progress = (note*100).toInt()
        binding.timeProg.progress = (time*100).toInt()
        binding.floatingActionButton.setOnClickListener{
            activity?.setResult(Activity.RESULT_OK)
            activity?.finish()
        }
    }
}
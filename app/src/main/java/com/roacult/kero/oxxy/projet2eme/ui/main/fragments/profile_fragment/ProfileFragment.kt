package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.profile_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.MainProfileBinding
import com.roacult.kero.oxxy.projet2eme.ui.main.CallbackFromActivity
import com.roacult.kero.oxxy.projet2eme.utils.LOG_TAG

class ProfileFragment : BaseFragment() ,CallbackFromActivity {
    companion object { fun getInstance() = ProfileFragment() }

    private lateinit var binding : MainProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MainProfileBinding.inflate(inflater,container,false)

        Log.v(LOG_TAG,"starting profile ...")

        val data = ArrayList<BarEntry>()
        data.add(BarEntry(9F, 30F))
        data.add(BarEntry(8F, 40F))
        data.add(BarEntry(7F, 50F))
        data.add(BarEntry(6F, 20F))
        data.add(BarEntry(5F, 60F))
        data.add(BarEntry(4F, 10F))
        data.add(BarEntry(3F, 0F))
        data.add(BarEntry(2F, 15F))
        data.add(BarEntry(1F, 70F))

        val barSet = BarDataSet(data,null)
        val barData = BarData(barSet)
        barData.barWidth = 0.7F


        binding.graphView.setTouchEnabled(false)
        binding.graphView.xAxis.setDrawAxisLine(false)
        binding.graphView.setDrawBarShadow(true)
        binding.graphView.setDrawValueAboveBar(true)
//        binding.graphView.setDrawHighlightArrow(true)
        binding.graphView.axisLeft.axisMinimum = 0f
        binding.graphView.axisRight.axisMinimum = 0f
        binding.graphView.data = barData
        binding.graphView.invalidate()
        binding.graphScroll.fullScroll(View.FOCUS_RIGHT)

        return binding.root
    }

    override fun showHelp(){}

    override fun showFilter() {}
}
package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.profile_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.roacult.kero.oxxy.domain.modules.User
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.MainProfileBinding
import com.roacult.kero.oxxy.projet2eme.ui.main.CallbackFromActivity
import com.roacult.kero.oxxy.projet2eme.utils.*
import com.roacult.kero.oxxy.projet2eme.utils.extension.visible
import com.squareup.picasso.Picasso

class ProfileFragment : BaseFragment() ,CallbackFromActivity {
    companion object { fun getInstance() = ProfileFragment() }

    private val viewModel by lazy{ViewModelProviders.of(this,viewModelFactory)[ProfileViewModel::class.java]}
    private lateinit var binding : MainProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MainProfileBinding.inflate(inflater,container,false)

        initViews()

        viewModel.observe(this){
            handleUserInfo(it.userInfo)
        }

        return binding.root
    }

    private fun handleUserInfo(userInfo: Async<User>) {
        when(userInfo){
            is Loading -> showLoading(true)
            is Fail<*, *> -> {
                showLoading(false)
                //TODO handle errors
            }
            is Success -> {
                showLoading(false)
                setUpUserData(userInfo())
            }
        }
    }

    private fun setUpUserData(userInfo: User) {
        if(userInfo.picture != null && userInfo.picture!!.isNotEmpty())
            Picasso.get().load(userInfo.picture).into(binding.image)
        binding.name.text = userInfo.fname + ", "+userInfo.lName
        binding.year.text = if(userInfo.year<2) (userInfo.year+1).toString()+" CPI"
        else (userInfo.year-1).toString()+" CS"
        binding.challenges.text = userInfo.nbSolved.toString()
        binding.points.text = userInfo.point.toString()
        binding.rank.text = userInfo.currentRank.toString()
        binding.graphView.data = getBarData(userInfo.ranks)
        binding.graphView.invalidate()
    }

    private fun getBarData(ranks: ArrayList<Int>) :BarData{
        val data = ArrayList<BarEntry>()
        for(rk in 0 until ranks.size){
            val rank =ranks[rk]
            data.add(BarEntry(rk.toFloat(),rank.toFloat()))
        }
        val barData = BarData(BarDataSet(data,null))
        barData.barWidth = 0.7f
        return barData
    }

    private fun showLoading(show: Boolean) {
        binding.graphView.visible(!show)
        binding.rankLoading.visible(show)
    }

    private fun initViews() {
        binding.toolbar.inflateMenu(R.menu.profile_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.settings -> {
                    //TODO open settings page
                    showMessage("//TODO open settings page")
                }
            }
            true
        }

        binding.graphView.setTouchEnabled(false)
        binding.graphView.xAxis.setDrawAxisLine(false)
        binding.graphView.setDrawBarShadow(true)
        binding.graphView.setDrawValueAboveBar(true)
        binding.graphView.axisLeft.axisMinimum = 0f
        binding.graphView.axisRight.axisMinimum = 0f
    }

    override fun showHelp(){}

    override fun showFilter() {}
}
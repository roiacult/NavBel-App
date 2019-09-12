package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.profile_fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.appbar.AppBarLayout
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.modules.SolvedChalenge
import com.roacult.kero.oxxy.domain.modules.User
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.MainProfileBinding
import com.roacult.kero.oxxy.projet2eme.ui.main.CallbackFromActivity
import com.roacult.kero.oxxy.projet2eme.ui.main.MainActivity
import com.roacult.kero.oxxy.projet2eme.ui.solvedchallenge.SolvedChallengeActivity
import com.roacult.kero.oxxy.projet2eme.ui.setting.SettingActivity
import com.roacult.kero.oxxy.projet2eme.utils.*
import com.roacult.kero.oxxy.projet2eme.utils.extension.visible
import com.squareup.picasso.Picasso

class ProfileFragment : BaseFragment() ,CallbackFromActivity {
    companion object {
        fun getInstance() = ProfileFragment()
        const val REQUEST_CODE = 16553
    }


    private val viewModel by lazy{ViewModelProviders.of(this,viewModelFactory)[ProfileViewModel::class.java]}
    private lateinit var binding : MainProfileBinding
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

    private val adapter = ChallengeAdapter(::startSolvedChallenge)

    private fun startSolvedChallenge(solvedChalenge: SolvedChalenge) {
        startActivityForResult(SolvedChallengeActivity.getIntent(context!!,solvedChalenge), REQUEST_CODE )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                //go to reward fragments
                (activity as? MainActivity)?.setSelectedItem(2)
            }
        }
    }

     fun reload(){
        viewModel.intialize()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MainProfileBinding.inflate(inflater,container,false)

        initViews()

        viewModel.observe(this){
            handleUserInfo(it.userInfo)
            handleChalenges(it.challenges)
        }

        return binding.root
    }

    private fun handleChalenges(challenges: Async<List<SolvedChalenge>>) {
        when(challenges) {
            is Loading -> {
                showLoadingInChalenges(true)
                showEmptyState(false)
            }
            is Fail<*,*> ->{
                showLoadingInChalenges(false)
                //TODO handle failures later
            }
            is Success -> {
                showLoadingInChalenges(false)
                val list = challenges()
                Log.v("solved_ch","solved : $list")
                if(list.isEmpty()) showEmptyState(true)
                else {
                    showEmptyState(false)
                    adapter.addAll(list)
                }
            }
        }
    }

    private fun showLoadingInChalenges(show: Boolean) {
        binding.challengeLoading.visible(show)
        binding.solvedChalenges.visible(!show)
    }

    private fun showEmptyState( show : Boolean ) {
        binding.emptyState.visible(show)
        binding.solvedChalenges.visibility = if(show )  View.INVISIBLE else View.VISIBLE
    }

    private fun handleUserInfo(userInfo: Async<User>) {
        when(userInfo){
            is Loading -> showLoadingInGraphView(true)
            is Fail<*, *> -> {
                showLoadingInGraphView(false)
                when(userInfo.error) {
                    Failure.GetUserInfoFailure.OperationFailed -> {
                        onError(R.string.user_info_failue)
                    }
                }
            }
            is Success -> {
                showLoadingInGraphView(false)
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
        binding.aboutUserName.text = "About ${userInfo.fname}"
        binding.userDesc.text = userInfo.description
        Log.v("user_rank"," : ${userInfo.ranks}")
        binding.graphView.data = getBarData(userInfo.ranks)
        binding.graphView.invalidate()
    }

    private fun getBarData(rk: List<Int>) :BarData{
        val ranks = ArrayList<Int>()
        val size = rk.size
        if( size <15 ) {
            for(i in 0 until (15 -size) )
                ranks.add(0)
            ranks.addAll(rk)
        }else {
            ranks.addAll(
                rk.subList(size-15 , size)
            )
        }

        val data = ArrayList<BarEntry>()
        for(i in 0 until ranks.size){
            val rank =ranks[i]
            data.add(BarEntry(i.toFloat(),rank.toFloat()))
        }
        val barData = BarData(BarDataSet(data,null))
        barData.barWidth = 0.7f
        return barData
    }

    private fun showLoadingInGraphView(show: Boolean) {
        binding.graphView.visible(!show)
        binding.rankLoading.visible(show)
    }

    private fun initViews() {
        binding.toolbar.inflateMenu(R.menu.profile_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.settings -> {
                    viewModel.withState {
                        val user = it.userInfo
                        if(user is Success)
                            startActivity(SettingActivity.getIntent(context!!,user()))
                    }
                }
                R.id.help -> {
                    (activity as? MainActivity)?.showHelp()
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

        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        binding.solvedChalenges.layoutManager = manager
        binding.solvedChalenges.adapter = adapter

        binding.appbar.addOnOffsetChangedListener(appBarStateListener)
    }

    private fun setToolbarTitle(b: Boolean) {
        if(b){
            binding.collapse.title =  ""
            return
        }
        viewModel.withState {
            val user =  (it.userInfo as? Success)?.invoke()
            if( user != null ) binding.collapse.title = user.fname + ", " +user.lName
        }
    }

    override fun showHelp(){
        TapTargetSequence(activity!!).apply {
            target(TapTarget.forToolbarMenuItem(binding.toolbar,R.id.settings,getString(R.string.settings_title),getString(R.string.settings_desc)))
            if(appBarStateListener.mCurrentState == AppBarStateChangeListener.State.EXPANDED){
                target(TapTarget.forView(binding.year,getString(R.string.year_desc)))
            }
            target(TapTarget.forView(binding.challenges,getString(R.string.challenges_title)))
            target(TapTarget.forView(binding.points,getString(R.string.point_title)))
            target(TapTarget.forView(binding.rank,getString(R.string.rank_title),getString(R.string.rank_desc)))
            target(TapTarget.forView(binding.aboutUserName,getString(R.string.desc_title)))
            target(TapTarget.forView(binding.blabllabla,getString(R.string.solvedChallenges_title)))
            start()
        }
    }

    override fun showFilter() {}
}
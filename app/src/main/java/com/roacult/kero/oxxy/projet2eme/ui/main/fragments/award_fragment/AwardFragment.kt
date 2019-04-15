package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.award_fragment

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.roacult.kero.oxxy.domain.modules.Award
import com.roacult.kero.oxxy.domain.modules.User
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.MainAwardPageBinding
import com.roacult.kero.oxxy.projet2eme.ui.main.CallbackFromActivity
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.extension.visible

class AwardFragment  : BaseFragment() , CallbackFromActivity {
    companion object { fun getInstance() = AwardFragment() }

    private lateinit var binding : MainAwardPageBinding

    private val viewModel by lazy { ViewModelProviders.of(this,viewModelFactory)[AwardViewModel::class.java] }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MainAwardPageBinding.inflate(inflater,container,false)

        binding.indicator.setViewPager(binding.awards)
        viewModel.getRewards()
        viewModel.observe(this){
            handleRewards(it.awards)
            handleUserInfo(it.userInfo)
        }

        return binding.root
    }

    private fun handleUserInfo(userInfo: Async<User>) {
        when(userInfo){
            is Fail<*,*>->{
                //TODO handle errors
            }
            is Success -> {
                binding.awards.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {}
                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                    override fun onPageSelected(position: Int) {
                        viewModel.withState {
                            val award = (it.awards as? Success)?.invoke()?.get(position) ?: return@withState
                            changeData(award)
                        }
                    }
                })
            }
        }
    }

    private fun handleRewards(awards: Async<List<Award>>) {
        when(awards){
            is Loading -> showLoading(true)
            is Fail<*,*> ->{
                showLoading(false)
                //TODO handle failures
            }
            is Success ->{
                showLoading(false)
                setViewWithData(awards())
            }
        }
    }

    private fun setViewWithData(awards: List<Award>) {
        binding.awards.adapter = AwardAdapter(awards.map { it.picture })
        binding.awards.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                val award = awards[position]
                binding.points.text = award.points.toString()
                changeData(award)
            }
        })
    }

    private fun changeData(award: Award) {
        viewModel.withState {
            val userInfo = (it.userInfo as? Success)?.invoke() ?: return@withState
            binding.points.setTextColor(if(userInfo.point >= award.points) Color.GREEN else Color.RED)
            binding.getreward.setOnClickListener {
                //TODO show alert dialogue to get rewards
            }
            binding.description.setOnClickListener {
                //TODO show description
            }
        }
        binding.indicator.setViewPager(binding.awards)
    }

    private fun showLoading(show: Boolean) {
        binding.awards.visible(!show)
        binding.loading.visible(show)
    }

    override fun showHelp(){

    }

    override fun showFilter() {}
}
package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.chalenge_fragment

import `in`.srain.cube.views.ptr.header.StoreHouseHeader
import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.MainChalengesBinding
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.extension.visible
import com.roacult.kero.oxxy.projet2eme.utils.extension.invisible

class ChalengeFragment : BaseFragment() {
    companion object { fun getInstance() = ChalengeFragment() }
    private lateinit var binding : MainChalengesBinding
    private val adapter = ChalengeAdapter()
    private val viewModel by lazy { ViewModelProviders.of(this,viewModelFactory)[ChalengeViewModel::class.java] }
    private val callback : CallbackFromViewModel by lazy { viewModel }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =DataBindingUtil.inflate(inflater,R.layout.main_chalenges,container,false)
        initialaze()

        viewModel.observe(this){
            it.getChalenges?.apply { handleChalenges(this) }
        }

        return binding.root
    }

    private fun handleChalenges(async: Async<List<ChalengeGlobale>>) {
        binding.refresh.setRefreshing(false)
        when(async){
            is Loading -> showLoading()
            is Fail<*,*> ->{
                when(async.error){
                    is Failure.GetAllChalengesFailure.OperationFailed -> showError(R.string.chalenge_cnx_prblm)
                    is Failure.GetAllChalengesFailure.OtherFailrue -> showError(R.string.chalenge_cnx_prblm)
                    is Failure.GetAllChalengesFailure.UserBannedTemp -> showError(R.string.banne_tmp)
                    is Failure.GetAllChalengesFailure.UserBannedForever -> {
                        //TODO show alert dialogue
                    }
                }
            }
            is Success ->{
                val list  = async()
                if(list.size !=0 ) setInRecycler(list)
                else showError(R.string.no_chalenge)
            }
        }
    }

    private fun setInRecycler(items : List<ChalengeGlobale>){
        binding.holder.shimmer.stopShimmer()
        binding.holder.shimmer.animate().apply {
            duration = 300
            alpha(0f)
            setListener(object: Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    binding.chalengeRecycler.visible()
                }
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })
            start()
        }
        binding.noChalenge.invisible()
        adapter.addAll(items)
    }

    private fun showError(@StringRes message: Int) {
        binding.chalengeRecycler.invisible()
        binding.holder.shimmer.stopShimmer()
        binding.noChalenge.setText(message)
        binding.holder.shimmer.animate().apply {
            duration = 300
            alpha(0f)
            setListener(object: Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    binding.noChalenge.visible()
                }
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })
            start()
        }
    }

    private fun showLoading() {
        binding.noChalenge.invisible()
        binding.holder.shimmer.startShimmer()
        binding.holder.shimmer.animate().apply {
            duration = 300
            alpha(1f)
            start()
        }
        binding.chalengeRecycler.invisible()
    }

    private fun initialaze(){
        binding.chalengeRecycler.adapter = adapter
        val manager = LinearLayoutManager(context!!)
        binding.chalengeRecycler.layoutManager = manager
        binding.refresh.setOnRefreshListener {
            callback.requestData()
        }
    }

    interface CallbackFromViewModel {
        fun requestData()
    }
}
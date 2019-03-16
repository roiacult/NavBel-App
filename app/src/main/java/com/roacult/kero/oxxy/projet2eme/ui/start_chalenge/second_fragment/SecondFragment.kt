package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.second_fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.StartChelngeViewModel
import com.roacult.kero.oxxy.projet2eme.databinding.StartChalengeFragmnt2Binding
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.extension.setEnable

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
            setSolvedNumbre(it.solved)
        }

        return binding.root
    }

    private fun initialize() {
        viewModel.withState {
            val detailles = (it.getChalengeDetailles as Success)()
            pagerAdapter= CardPagerAdapter(binding.questionsContainer::getCurrentItem)
            pagerAdapter.addAllCards(detailles.questions)
            val animationPager = ScallingPagerAnimation(pagerAdapter,viewModel::setPage)
            binding.questionsContainer.addOnPageChangeListener(animationPager)
            binding.questionsContainer.adapter = pagerAdapter
        }
        binding.time.startTimer(viewModel.lastTime.toLong()){
            showDialogueFinish(R.string.time_finish,R.string.time_finish_msg)
            //TODO unsubscribe observer
        }
        binding.perv.setOnClickListener {
            binding.questionsContainer.setCurrentItem(binding.questionsContainer.currentItem-1,true)
        }
    }

    private fun setUpPage(page: Int) {
        if(page == 0) binding.perv.setEnable(false)
        else binding.perv.setEnable(true)
        if(page == viewModel.size-1) {
            binding.next.setText(R.string.submit)
            binding.next.setOnClickListener { performSubmition() }
        }else{
            binding.next.setText(R.string.next)
            binding.next.setOnClickListener {
                binding.questionsContainer.setCurrentItem(binding.questionsContainer.currentItem+1,true)
            }
        }
    }

    private fun performSubmition() {
        showMessage("//TODO performing submition")
    }

    private fun setSolvedNumbre(solved: Int) {
        binding.solved.text= solved.toString()+"/5"
        if(solved >= 5) {
            showDialogueFinish(R.string.chalneg_solved,R.string.chalneg_solved_msg)
        }
    }

    private fun showDialogueFinish(@StringRes title : Int ,@StringRes msg : Int){
        AlertDialog.Builder(context!!).apply {
            setTitle(title)
            setMessage(msg)
            setPositiveButton(R.string.continue_challenge) { _, _ -> }
            setNegativeButton(R.string.cancel) { _, _ ->
                //TODO finish activity and go back to main
            }
            show()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        viewModel.lastTime = binding.time.time.toInt()
    }
}
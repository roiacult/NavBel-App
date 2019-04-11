package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.chalengefragment

import android.app.ProgressDialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.roacult.kero.oxxy.domain.interactors.SubmitionResult
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.StartChelngeViewModel
import com.roacult.kero.oxxy.projet2eme.databinding.StartChalengeFragmnt2Binding
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.extension.questionSolved
import com.roacult.kero.oxxy.projet2eme.utils.extension.setEnable

class ChalengeFragment :BaseFragment() {

    private val viewModel : StartChelngeViewModel by lazy { ViewModelProviders.of(activity!!,viewModelFactory)[StartChelngeViewModel::class.java]}
    private lateinit var binding : StartChalengeFragmnt2Binding
    private lateinit var pagerAdapter: CardPagerAdapter
    private val progressDialogue : ProgressDialog by lazy {
        ProgressDialog(context!!).apply {
            isIndeterminate = false
            setTitle(getString(R.string.subitting))
            setMessage(getString(R.string.subiting_msg))
        }
    }

    companion object { fun getInstance() = ChalengeFragment() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.start_chalenge_fragmnt2,container,false)

        initialize()
        viewModel.observe(this){
            it.page.getContentIfNotHandled()?.apply { setUpPage(this) }
            setSolvedNumbre(it.solvedBy)
            setSolving(it.questionSolved)
            it.submition?.apply { handleSubmissionResult(this)}
        }

        return binding.root
    }

    private fun handleSubmissionResult(submition: Async<SubmitionResult>) {
        when(submition){
            is Loading ->{progressDialogue.show()}
            is Success -> {
                progressDialogue.hide()
                viewModel.gotoResultFragment()
            }
            is Fail<*,*>->{
                progressDialogue.hide()
                //TODO handle different fails
            }
        }
    }

    private fun initialize() {
        viewModel.withState {
            val detailles = (it.getChalengeDetailles as Success)()
            pagerAdapter= CardPagerAdapter(binding.questionsContainer::getCurrentItem,viewModel)
            pagerAdapter.addAllCards(detailles.questions)
            val animationPager = ScallingPagerAnimation(pagerAdapter,viewModel::setPage)
            binding.questionsContainer.addOnPageChangeListener(animationPager)
            binding.questionsContainer.adapter = pagerAdapter
        }
        binding.time.startTimer(viewModel.lastTime){
            showDialogueFinish(R.string.time_finish,R.string.time_finish_msg)
            viewModel.unsubscribe()
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
        val answers = viewModel.answers
        if(answers.questionSolved == viewModel.size){
            viewModel.submitAnswer(binding.time.time)
        }else{
            onError("please answer all questions first!!")
        }
    }

    private fun setSolvedNumbre(solved: Int) {
        binding.solved.text= solved.toString()+"/5"
        if(solved >= 5) {
            showDialogueFinish(R.string.chalneg_solved,R.string.chalneg_solved_msg)
        }
    }

    private fun setSolving(questionSolved: Int) {
        binding.quesSolved.text = questionSolved.toString()+"/"+viewModel.size.toString()
    }

    private fun showDialogueFinish(@StringRes title : Int ,@StringRes msg : Int){
        AlertDialog.Builder(context!!).apply {
            setTitle(title)
            setMessage(msg)
            setPositiveButton(R.string.continue_challenge) { _, _ -> }
            setNegativeButton(R.string.cancel) { _, _ ->
                activity?.finish()
            }
            show()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        viewModel.lastTime = binding.time.time
    }
}
package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.chalengefragment

import android.animation.ValueAnimator
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
import kotlinx.android.synthetic.main.start_chalenge_pager_card.view.*



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

    companion object {
        fun getInstance() = ChalengeFragment()
        const val TIME_FOR_QUESTION = 2000L
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.start_chalenge_fragmnt2,container,false)

        initialize()
        viewModel.observe(this){
            it.page.getContentIfNotHandled()?.apply { setUpPage(this) }
            setSolvedNumbre(it.solvedBy)
            setSolving(it.questionSolved)
            it.submition?.apply { handleSubmissionResult(this)}
            it.writeAnswer?.getContentIfNotHandled()?.apply { handleComparison(this) }
        }
        return binding.root
    }

    private fun handleComparison(answer: Long) {
        if(answer == -1L ) return
        binding.time.pauseTime = true
        viewModel.withState {
            val adapter = pagerAdapter.getCardViewAt(it.page.peekContent())?.options_recycler?.adapter as? CardPagerAdapter.OptionRecyclerAdapter
            adapter?.writeAnswer = answer
            adapter?.notifyDataSetChanged()
            viewModel.curentQuestion = null
            ValueAnimator.ofInt(0,1).apply{
                duration = TIME_FOR_QUESTION
                addUpdateListener {
                    if(it.animatedValue as Int == 1){
                        binding.questionsContainer.setCurrentItem(binding.questionsContainer.currentItem+1,true)
                        startTimer()
                    }
                }
                start()
            }
        }
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
        startTimer()
        binding.next.setOnClickListener {
            if ( viewModel.curentQuestion == null ) {
                //TODO
                showDialogueFinish(R.string.not_answerd_title, R.string.not_answerd_msg)
                return@setOnClickListener
            }
//            enableDisableViewGroup(binding.root as ViewGroup,false)
            viewModel.compare()
        }
    }

    private fun startTimer() {
        binding.time.startTimer(viewModel.lastTime){
            showDialogueFinish(R.string.time_finish, R.string.time_finish_msg)
            viewModel.onTimeFinished()
        }
    }

    private fun setUpPage(page: Int) {
        if(page == viewModel.size-1) {
            binding.next.setText(R.string.submit)
            binding.next.setOnClickListener { performSubmition() }
        }
    }

    private fun performSubmition() {
        viewModel.unsubscribe()
        val answers = viewModel.userAnswers
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
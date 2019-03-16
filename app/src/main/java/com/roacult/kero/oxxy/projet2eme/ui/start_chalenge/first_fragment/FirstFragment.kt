package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.first_fragment

import android.animation.Animator
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.modules.ChalengeDetailles
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.StartChalengeFragment1Binding
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.*
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.squareup.picasso.Picasso
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.extension.visible
import com.roacult.kero.oxxy.projet2eme.utils.extension.invisible

const val PERMITION_EXTRENAL_STORAGE = 145368

class FirstFragment : BaseFragment(){

    companion object { fun getInstance() = FirstFragment() }

    private val viewModel : StartChelngeViewModel by lazy { ViewModelProviders.of(activity!!,viewModelFactory)[StartChelngeViewModel::class.java]}
    private val callback : CallbackToViewModel by lazy { viewModel }
    private lateinit var binding : StartChalengeFragment1Binding
    private val adapter by lazy { ResourceAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.start__chalenge_fragment1,container,false)

        if(arguments == null) Log.v("bullshit","arguments are null")
        if(callback.isItFirstTime())saveDataToViewModel(arguments!!)
        else setGlobalDate()

        initialize()

        viewModel.observe(this){
            handleChelengeDetailesresult(it.getChalengeDetailles)
        }

        return binding.root
    }

    private fun initialize() {
        binding.resourcesRecycler.adapter = adapter
        binding.resourcesRecycler.layoutManager = LinearLayoutManager(context)
        binding.resourcesRecycler.setHasFixedSize(true)
    }

    private fun handleChelengeDetailesresult(chalengeDetailles: Async<ChalengeDetailles>) {
        when(chalengeDetailles){
            is Loading -> showLoading()
            is Success -> setInRecycler(chalengeDetailles())
            is Fail<*,*> -> {
                when(chalengeDetailles.error){
                    is Failure.GetChalengeDetailsFailure.ChallengeAlreadySolved ->showFaile(R.string.chalenge_not_availible)
                    is Failure.GetChalengeDetailsFailure.UserBannedTemp -> showFaile(R.string.banne_temp)
                    is Failure.GetChalengeDetailsFailure.OperationFailed -> showFaile(R.string.chale_detai_op_fail)
                }
            }
        }
    }

    private fun showFaile(msg: Int) {
        binding.notAvailible.setText(msg)
        binding.resourcesRecycler.invisible()
        binding.textView20.invisible() // notice texte
        binding.start.isClickable = false
        binding.shimmer.loadingShimmer.stopShimmer()
        binding.shimmer.loadingShimmer.animate().apply {
            alpha(0f)
            duration = 300
            setListener(object: Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    binding.notAvailible.visible()
                }
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })
            start()
        }
    }


    private fun setInRecycler(chalengeDetailles: ChalengeDetailles) {
        binding.start.isClickable = true
        binding.start.setOnClickListener{ showStartDialogue() }
        adapter.addAll(chalengeDetailles.resources.map { Resource(it)})
        binding.time.text = (chalengeDetailles.time/60).toString()
        binding.shimmer.loadingShimmer.animate().apply {
            duration = 300
            alpha(0f)
            setListener(object: Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    binding.resourcesRecycler.visible()
                }
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })
            start()
        }
        binding.shimmer.loadingShimmer.stopShimmer()
        binding.notAvailible.invisible()
        binding.textView20.visible()
    }

    private fun showStartDialogue() {
        AlertDialog.Builder(context!!).
                setTitle(R.string.before_start).
                setMessage(R.string.notice_befor_start_chalenge).
                setNegativeButton(R.string.cancel){_,_->}.
                setPositiveButton(R.string.start) { _, _->
                    callback.startChalenge()
                }.
                show()
    }

    private fun showLoading() {
        binding.start.isClickable = false
        binding.resourcesRecycler.invisible()
        binding.textView20.invisible()
        binding.notAvailible.invisible()
        binding.shimmer.loadingShimmer.animate().apply {
            alpha(1f)
            duration = 300
            start()
        }
        binding.shimmer.loadingShimmer.startShimmer()
    }

    private fun setGlobalDate() {
        Picasso.get().load(viewModel.chalengeGlobale.image).noFade().into(binding.imageView5)
        binding.textView19.text = viewModel.chalengeGlobale.module // module text view
        binding.points.text = viewModel.chalengeGlobale.point.toString()
        binding.solved.text = viewModel.chalengeGlobale.nbPersonSolveded.toString()
        binding.questions.text = viewModel.chalengeGlobale.nbOfQuestions.toString()
    }

    private fun saveDataToViewModel(arguments: Bundle) {
        val id = arguments.getInt(CHALENGE_ID)
        val module = arguments.getString(CHALENGE_MODULE)!!
        val story = arguments.getString(CHALENGE_STORY)!!
        val image = arguments.getString(CHALENGE_IMAGE)!!
        val point = arguments.getInt(CHALENGE_POINT)
        val question = arguments.getInt(CHALENGE_QUESTION)
        val solved = arguments.getInt(CHALENGE_SOLVED)
        callback.saveData(ChalengeGlobale(id,module,story,image,point,solved,question))
        setGlobalDate()
    }

    interface CallbackToViewModel{
        fun fetchData()
        fun saveData(chalengeGlobale: ChalengeGlobale)
        fun startChalenge()
        fun isItFirstTime(): Boolean
    }
}

package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.award_fragment.getgift

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RawRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.modules.Award
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.databinding.MainAwardGetgiftBinding
import com.roacult.kero.oxxy.projet2eme.utils.Async
import javax.inject.Inject
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.extension.visible
import com.roacult.kero.oxxy.projet2eme.utils.extension.inTransaction
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

class GetGiftFragment : BottomSheetDialogFragment() , HasSupportFragmentInjector {

    companion object {

        const val GET_GIFT_TAG = "com.roacult.kero.oxxy.projet2eme:GetGift"
        const val GIFT_ID ="com.roacult.kero.oxxy.projet2eme:GiftId"
        const val GIFT_POINTS= "com.roacult.kero.oxxy.projet2eme:GiftPoints"

        fun getInstance(award : Award) :GetGiftFragment{
            val fragment = GetGiftFragment()
            val bundle = Bundle()
            bundle.putString(GIFT_ID,award.aid)
            bundle.putInt(GIFT_POINTS,award.points)
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding : MainAwardGetgiftBinding
    private val viewModel by lazy {ViewModelProviders.of(this,viewModelFactory)[GetGiftViewModel::class.java]}
    private val animator =  ValueAnimator.ofFloat(0f,1f)

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MainAwardGetgiftBinding.inflate(inflater,container,false)

        binding.textView23.text = arguments!!.getInt(GIFT_POINTS).toString()

        binding.button.setOnClickListener{
            viewModel.getGift(arguments!!.getString(GIFT_ID)!!)
        }

        viewModel.observe(this){
            it.getGiftOp?.apply { handleOp(this) }
        }

        return binding.root
    }

    private fun handleOp(async: Async<None>) {
        when(async){
            is Loading -> showLoading(true)
            is Fail<*,*> -> {
                showLoading(false)
                //TODO handle Errors
                playAnimation(R.raw.failed)
            }
            is Success -> {
                showLoading(false)
                opSuccess()
            }
        }
    }

    private fun opSuccess(){
        playAnimation(R.raw.success)
        binding.textView25.text = getString(R.string.chek_email)
        binding.button.text =getString(R.string.close)
        binding.button.setOnClickListener {
            activity?.supportFragmentManager?.inTransaction{
                remove(activity!!.supportFragmentManager.findFragmentByTag(GET_GIFT_TAG)!!)
            }
        }
    }

    private fun playAnimation(@RawRes animation : Int){
        binding.lottieAnimationView.setAnimation(animation)
        animator.pause()
        animator.apply {
            duration = 800
            addUpdateListener {
                val value = it.animatedValue as Float
                binding.lottieAnimationView.progress = value
            }
            start()
        }
    }

    private fun showLoading(show: Boolean) {
        binding.button.isClickable = !show
        binding.button.alpha = if(show) 0.5f else 1f
        binding.lottieAnimationView.visibility = if(show) View.INVISIBLE else View.VISIBLE
        binding.progressBar2.visible(show)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =childFragmentInjector
}
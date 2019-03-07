package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.reset_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.ResetPasswordFragmentBinding
import com.roacult.kero.oxxy.projet2eme.utils.extension.isEmailValid

class ResetPasswordFragment : BaseFragment() {

    companion object { fun  getInstance() = ResetPasswordFragment() }

    private lateinit var binding : ResetPasswordFragmentBinding
    private val viewModel : RestPasswordViewModel by lazy { ViewModelProviders.of(this,viewModelFactory)[RestPasswordViewModel::class.java] }
    private val callback : CallbackFromViewModel by lazy {viewModel}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.reset_password_fragment,container,false)


        viewModel.observe(this){
            setUpState(it.viewState)
        }

        return binding.root
    }

    private fun setUpState(viewState: Int) {
        when (viewState){
            REST_PASS_STATE_SEND -> {
                binding.motion.transitionToState(R.id.start)
                binding.submit.setOnClickListener{ performSendingemail(binding.email.text.toString()) }
                binding.submit.setText(R.string.send)
            }
            REST_PASS_STATE_CONFIRM -> {
                binding.motion.transitionToState(R.id.end)
                binding.submit.setOnClickListener{ performConfirmation(binding.code.text.toString()) }
                binding.submit.setText(R.string.confirm)
                binding.resendBtn.setOnClickListener{performResendingEmail()}
            }
        }
    }
    private fun performSendingemail(email: String) {
        if(!email.isEmailValid()){onError(R.string.email_not_valid); return }
        callback.sendEmail(email)
    }

    private fun performConfirmation(code: String) {
        if(code.isEmpty() || code.length != 5){ onError(R.string.confirm_code_not_valid); return}
        callback.confirmCode(code)
    }

    private fun performResendingEmail(){

    }
    fun transiteToStart(){

    }

    interface CallbackFromViewModel {
        fun setView(state : Int)
        fun sendEmail(email: String)
        fun confirmCode(code :String)
    }
}
package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.reset_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.ResetPasswordFragmentBinding
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.RegistrationActivity
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_signin_login.RESEND_EMAIL_TIME
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.extension.isEmailValid
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.Fail

@Suppress("DUPLICATE_LABEL_IN_WHEN")
class ResetPasswordFragment : BaseFragment() , RegistrationActivity.CallbackToFragment {

    companion object { fun  getInstance() = ResetPasswordFragment() }

    private lateinit var binding : ResetPasswordFragmentBinding
    private val viewModel : RestPasswordViewModel by lazy { ViewModelProviders.of(this,viewModelFactory)[RestPasswordViewModel::class.java] }
    private val callback : CallbackFromViewModel by lazy {viewModel}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.reset_password_fragment,container,false)


        viewModel.observe(this){
            setUpState(it.viewState)
            it.sendCodeToEmailOp?.getContentIfNotHandled()?.apply { handleSendingEmailResult(this) }
            it.confirmEmailOp?.getContentIfNotHandled()?.apply { handleConfirmResult(this) }
            it.resendCodeToEmailOp?.getContentIfNotHandled()?.apply { handleResendigCodeResult(this) }
        }

        return binding.root
    }

    private fun handleSendingEmailResult(op: Async<None>) {
        when(op){
            is Loading -> showLoading(true)
            is Fail<*,*> ->{
                showLoading(false)
                when(op.error){
                    //TODO handle diffrent errors
                }
            }
            is Success -> {
                showLoading(false)
                viewModel.email = binding.email.text.toString()
                callback.setView(REST_PASS_STATE_CHANGE)
            }
        }
    }

    private fun handleConfirmResult(op: Async<None>) {
        when(op){
            is Loading -> showLoading(true)
            is Fail<*,*> ->  {
                showLoading(false)
                when(op.error){
                    //TODO handle diffrent errors
                }
            }
            is Success -> {
                showLoading(false)
                callback.setView(REST_PASS_STATE_CHANGE)
            }
        }
    }

    private fun handleResendigCodeResult(op: Async<None>) {
        when(op){
        }
    }

    private fun setUpState(viewState: Int) {
        when (viewState){
            REST_PASS_STATE_SEND -> {
                binding.motion.transitionToState(R.id.start)
                binding.submit.setOnClickListener{ performSendingemail(binding.email.text.toString()) }
                binding.submit.setText(R.string.send)
                viewModel.email = ""
            }
            REST_PASS_STATE_CONFIRM -> {
                binding.motion.transitionToState(R.id.end)
                binding.submit.setOnClickListener{ performConfirmation(binding.code.text.toString()) }
                binding.submit.setText(R.string.confirm)
                binding.resendBtn.setOnClickListener{performResendingEmail()}
                binding.code.setText("")
            }
            REST_PASS_STATE_CHANGE -> {
                //TODO don't forget this state
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
        if(System.currentTimeMillis()-viewModel.lastSendTime< RESEND_EMAIL_TIME){
            onError(R.string.resend_code_time_limit)
            return
        }
        callback.resendEmail()
    }

    private fun showLoading(show: Boolean) {
        //TODO show loading
    }

    override fun goToDefaultState() {
        callback.setView(REST_PASS_STATE_SEND)
    }

    override fun shouldWeGoToDefaultState(): Boolean {
        var shouldWeGo = false
        viewModel.withState {
            shouldWeGo = it.viewState == REST_PASS_STATE_CONFIRM
        }
        return shouldWeGo
    }

    interface CallbackFromViewModel {
        fun setView(state : Int)
        fun sendEmail(email: String)
        fun resendEmail()
        fun confirmCode(code :String)
    }
}
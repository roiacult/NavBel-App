package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.reset_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
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
import com.roacult.kero.oxxy.projet2eme.utils.extension.visible
import com.roacult.kero.oxxy.projet2eme.utils.extension.loading
import kotlinx.android.synthetic.main.reset_password_fragment.view.*

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
            it.changinPassOp?.getContentIfNotHandled()?.apply { handleChangePasswordOp(this) }
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
                callback.setView(REST_PASS_STATE_CONFIRM)
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
            is Loading -> showLoading(true)
            is Fail<*,*> ->  {
                showLoading(false)
                when(op.error){
                    //TODO
                }
            }
            is Success ->{
                showLoading(false)
//                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    private fun handleChangePasswordOp(op: Async<None>) {
        when(op){
            is Loading -> showLoading(true)
            is Fail<*,*> -> {
                showLoading(false)
                when(op.error){
                    //TODO handle difrren erros
                }
            }
            is Success -> {
                showLoading(false)
                showMessage(R.string.pass_change_success)
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    private fun setUpState(viewState: Int) {
        when (viewState){
            REST_PASS_STATE_SEND -> {
                binding.motion.transitionToState(R.id.start)
                binding.submit.setOnClickListener{ performSendingemail(binding.email.text.toString()) }
                binding.submit.setText(R.string.send)
                binding.title.setText(R.string.forgot_password)
                viewModel.email = ""
            }
            REST_PASS_STATE_CONFIRM -> {
                binding.motion.transitionToState(R.id.end)
                binding.submit.setOnClickListener{ performConfirmation(binding.code.text.toString()) }
                binding.submit.setText(R.string.confirm)
                binding.resendBtn.setOnClickListener{performResendingEmail()}
                binding.title.setText(R.string.verification)
                binding.code.setText("")
            }
            REST_PASS_STATE_CHANGE -> {
                binding.motion.transitionToState(R.id.final_state)
                binding.submit.setOnClickListener{changePassword(binding.newPass.text.toString())}
                binding.submit.setText(R.string.change_pass)
                binding.title.setText(R.string.change_pass_title)
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

    private fun changePassword(newPass: String) {
        if(newPass.length<8){
            onError(R.string.pass_short)
            return
        }
        callback.changePassword(newPass)
    }

    private fun showLoading(show: Boolean) {
        binding.loading.alpha = if(show) 1f else 0f
        binding.sendEmail.visible(!show)
        binding.confirm.visible(!show)
        binding.changePass.visible(!show)
        binding.submit.loading(show)
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
        fun changePassword(password : String)
    }
}
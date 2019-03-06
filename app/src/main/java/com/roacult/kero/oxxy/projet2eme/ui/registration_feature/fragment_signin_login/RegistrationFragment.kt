package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_signin_login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.interactors.MailResult
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.RegistrationFragmentBinding
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.RegistrationActivity
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_saveinfo.SAVEINFO_EMAIL
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_saveinfo.SAVEINFO_FIRST_NAME
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_saveinfo.SAVEINFO_LAST_NAME
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_saveinfo.SAVEINFO_YEAR
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.extension.isEmailValid
import com.roacult.kero.oxxy.projet2eme.utils.extension.loading
import com.roacult.kero.oxxy.projet2eme.utils.extension.visible
import java.sql.Time
import java.util.*

class RegistrationFragment : BaseFragment() , RegistrationActivity.CallbackToFragment {

    companion object { fun getInstance() = RegistrationFragment() }

    private lateinit var binding : RegistrationFragmentBinding
    private val viewModel : RegistrationViewModel by lazy {ViewModelProviders.of(this,viewModelFactory)[RegistrationViewModel::class.java]}
    private val callback : CallbackFromViewModel by lazy {viewModel}
    private  var callbackToActivity : CallbackToRegistrationActivity? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.registration_fragment,container,false)

        viewModel.observe(this){
            setUpState(it.viewState)
            it.logInOperation?.getContentIfNotHandled()?.apply { handleLoginOperation(this) }
            it.signInOperation?.getContentIfNotHandled()?.apply { handleSignInOperation(this) }
            it.confirmatioOperation?.getContentIfNotHandled()?.apply { handleConfirmeOperation(this) }
            it.resendOperation?.getContentIfNotHandled()?.apply { handleResendConfirmationOperration(this) }
        }

        return binding.root
    }

    private fun handleLoginOperation(logInOperation: Async<None>) {
        when(logInOperation){
            is Loading -> showLoading(true)
            is Fail<*, *> -> {
                showLoading(false)
                when (logInOperation.error){
                    is Failure.LoginFaillure.UserNotSubscribedYet -> {
                        onError(R.string.not_subscribed_yet)
                        callback.setView(REGISTRATION_STATE_SIGNIN)
                    }
                    is Failure.LoginFaillure.UserBanned -> {
                        showDialoguUserBanned()
                        callback.setView(REGISTRATION_STATE_DEFAULT)
                    }
                    is Failure.LoginFaillure.WrongPassword -> onError(R.string.wrong_password)
                    is Failure.LoginFaillure.AutherFaillure -> onError(R.string.login_failled)
                }
            }
            is Success -> {
                showLoading(false)
                //TODO goto main
                showMessage("go to main know")
            }
            else -> showLoading(false)
        }
    }

    private fun handleSignInOperation(signInOperation: Async<MailResult>) {
        when(signInOperation){
            is Loading -> {
                showLoading(true)
                Log.e("sprint2","on loading")
            }
            is Fail<*, *> -> {
                Log.e("sprint2","on faille")
                showLoading(false)
                when(signInOperation.error){
                    is Failure.SignInFaillure.UserNotFoundFaillurre -> onError(R.string.email_not_found)
                    is Failure.SignInFaillure.AutherFaillure -> onError(R.string.signin_failled)
                    is Failure.SignInFaillure.UserAlreadyExist ->{
                        onError(R.string.user_alredy_subscribe)
                        callback.setView(REGISTRATION_STATE_LOGIN)
                    }
                    is Failure.SignInFaillure.UserBanned -> {
                        showDialoguUserBanned()
                        callback.setView(REGISTRATION_STATE_DEFAULT)
                    }
                    is Failure.SignInFaillure.CodeSendingError -> onError(R.string.code_sending_error)

                }
            }
            is Success -> {
                Log.v("sprint2","on success")
                showLoading(false)
                callback.setView(REGISTRATION_STATE_CONFIRM)
                val result = signInOperation()
                callback.setUserInfo(result.nom,result.prenom,result.year)
            }
        }
    }

    private fun handleConfirmeOperation(async: Async<None>) {
        when(async){
            is Loading ->showLoading(true)
            is Fail<*, *> -> {
                showLoading(false)
                when(async.error){
                    is Failure.ConfirmEmailFaillure.CadeNotCorrect -> onError(R.string.code_not_correct)
                    is Failure.ConfirmEmailFaillure.MaximumNumbreOfTry ->{
                        onError(R.string.try_out)
                        callback.setView(REGISTRATION_STATE_DEFAULT)
                    }
                    is Failure.ConfirmEmailFaillure.AutherFaillur -> onError(R.string.confirmation_error)
                }
            }
            is Success -> {
                showLoading(false)
                gotoSaveInfo()
            }
        }
    }

    private fun handleResendConfirmationOperration(resend: Async<None>) {
        when(resend){
            is Loading -> showLoadingForConfirmation(true)
            is Success -> showLoadingForConfirmation(false)
            is Fail<*,*> -> {
                showLoadingForConfirmation(false)
                onError(R.string.resend_failled)
            }
        }
    }


    private fun showLoading(b: Boolean) {
        binding.loginBtn.loading(b)
        binding.signinBtn.loading(b)
        binding.progress.alpha = if(b) 1f else 0f
        binding.signinInputs.visible(!b)
        binding.loginInputs.visible(!b)
        binding.confirmInputs.visible(!b)
    }

    private fun showLoadingForConfirmation(show: Boolean) {
        binding.resendBtn.loading(show)
        binding.confirmInputs.visible(!show)
        binding.progress.alpha = if(show) 1f else 0f
        binding.confirmText.isClickable = !show
    }

    private fun setUpState(state: Int) {
        when(state ){
            REGISTRATION_STATE_DEFAULT -> {
                binding.motion.transitionToState(R.id.state_default)
                binding.signinBtn.setText(R.string.signin)
                binding.loginBtn.setOnClickListener{ callback.setView(REGISTRATION_STATE_LOGIN) }
                binding.signinBtn.setOnClickListener{ callback.setView(REGISTRATION_STATE_SIGNIN) }
            }
            REGISTRATION_STATE_LOGIN -> {
                binding.motion.transitionToState(R.id.state_login)
                binding.loginBtn.setOnClickListener{ performeLogin() }
            }
            REGISTRATION_STATE_SIGNIN -> {
                binding.motion.transitionToState(R.id.state_signin)
                binding.signinBtn.setText(R.string.signin)
                binding.signinBtn.setOnClickListener{ performSignin() }
            }
            REGISTRATION_STATE_CONFIRM -> {
                binding.motion.transitionToState(R.id.state_confirm)
                binding.signinBtn.setText(R.string.confirm_email)
                binding.signinBtn.setOnClickListener{ confirmEmail(binding.confirmText.text.toString())}
                binding.resendBtn.setOnClickListener{resendConfirmationCode()}
                viewModel.lastResendTime = System.currentTimeMillis()
            }
        }
    }

    private fun resendConfirmationCode() {

        if(System.currentTimeMillis() - viewModel.lastResendTime< RESEND_EMAIL_TIME ){
            onError(R.string.resend_code_time_limit)
            return;
        }
        callback.resendConfirmationCode()
    }

    private fun performSignin() {
        val email :String = binding.signinEmail.text.toString()
        if(!email.isEmailValid()){ onError(R.string.email_not_valid);return }
        if (!isNetworkConnected()){ onError(R.string.no_internet);return }
        callback.signIn(email)
    }

    private fun performeLogin() {
        val email : String = binding.loginEmail.text.toString()
        val password : String = binding.loginPassword.text.toString()

        if(!email.isEmailValid()){ onError(R.string.email_not_valid) ;return }
        if(password.length<8) { onError(R.string.password_short);return }
        if (!isNetworkConnected()){ onError(R.string.no_internet);return }
        callback.login(email,password)
    }

    private fun confirmEmail(code: String) {
        if(code.isEmpty() || code.length != 5) {onError(R.string.confirm_code_not_valid);return}
        callback.confirmEmail(code)
    }

    private fun gotoSaveInfo() {
        Log.v("sprint2","go to save info (in fragment)")
        val bundle = Bundle()
        bundle.putString(SAVEINFO_EMAIL,binding.signinEmail.text.toString())
        bundle.putString(SAVEINFO_FIRST_NAME, viewModel.name)
        bundle.putString(SAVEINFO_LAST_NAME, viewModel.lastName)
        bundle.putInt(SAVEINFO_YEAR, viewModel.year)
        setUpCallbackToActivity()
        callbackToActivity?.openSaveInfoFragment(bundle)
    }

    private fun showDialoguUserBanned(){
        androidx.appcompat.app.AlertDialog.Builder(context!!)
            .setTitle(R.string.banned_title)
            .setMessage(R.string.banned_message)
            .show()
    }

    private fun setUpCallbackToActivity(){callbackToActivity = activity as? RegistrationActivity}

    override fun goToDefaultState() { callback.setView(REGISTRATION_STATE_DEFAULT) }

    override fun shouldWeGoToDefaultState() : Boolean {
        var gotoDefaultState = false
        viewModel.withState {
            gotoDefaultState = it.viewState == REGISTRATION_STATE_SIGNIN
                    || it.viewState == REGISTRATION_STATE_LOGIN
                    || it.viewState == REGISTRATION_STATE_CONFIRM
        }
        return gotoDefaultState
    }

    interface CallbackFromViewModel{
        fun setView(state : Int)
        fun login(email : String,password : String)
        fun signIn(email: String)
        fun confirmEmail(code : String)
        fun setUserInfo(name : String , lastName : String , year : Int)
        fun resendConfirmationCode()
    }
}
interface CallbackToRegistrationActivity{
    fun openSaveInfoFragment(bundle : Bundle)
}
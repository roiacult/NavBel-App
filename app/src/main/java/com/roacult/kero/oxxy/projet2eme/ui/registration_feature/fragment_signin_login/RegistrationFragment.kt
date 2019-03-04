package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_signin_login

import android.app.AlertDialog
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
        }

        return binding.root
    }

    private fun handleSignInOperation(signInOperation: Async<MailResult>) {
        when(signInOperation){
            is Loading -> {
                showLoading(true)
                Log.v("sprint2","on loading")
            }
            is Fail<*> -> {
                Log.v("sprint2","on faille")
                showLoading(false)
                when(signInOperation.error){
                    is Failure.SignInFaillure.UserNotFoundFaillurre -> onError(R.string.email_not_found)
                    is Failure.SignInFaillure.AutherFaillure -> onError(R.string.signin_failled)
                    is Failure.SignInFaillure.UserAlreadyExist ->{
                        onError(R.string.user_alredy_subscribe)
                        callback.setView(REGISTRATION_STATE_LOGIN)
                    }
                    is Failure.SignInFaillure.UserBanned -> {showDialoguUserBanned()}
                }
            }
            is Success -> {
                showLoading(false)
                Log.v("sprint2","on success")
                callback.setView(REGISTRATION_STATE_CONFIRM)
            }
        }
    }

    private fun gotoSaveInfo(signInOperation: MailResult) {
        Log.v("sprint2","go to save info (in fragment)")
        val bundle = Bundle()
        bundle.putString(SAVEINFO_EMAIL,binding.signinEmail.text.toString())
        bundle.putString(SAVEINFO_FIRST_NAME, signInOperation.nom)
        bundle.putString(SAVEINFO_LAST_NAME, signInOperation.prenom)
        bundle.putInt(SAVEINFO_YEAR, signInOperation.year )
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

    private fun handleLoginOperation(logInOperation: Async<None>) {
        when(logInOperation){
            is Loading -> showLoading(true)
            is Fail<*> -> {
                showLoading(false)
                when (logInOperation.error){
                    is Failure.LoginFaillure.UserNotSubscribedYet -> {
                        onError(R.string.not_subscribed_yet)
                        callback.setView(REGISTRATION_STATE_SIGNIN)
                    }
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

    private fun showLoading(b: Boolean) {
        binding.loginBtn.loading(b)
        binding.signinBtn.loading(b)
        binding.progress.alpha = if(b) 1f else 0f
        binding.signinInputs.visible(!b)
        binding.loginInputs.visible(!b)
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
                binding.signinBtn.setOnClickListener{ callback.confirmEmail(binding.confirmText.text.toString()) }
            }
        }
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

    override fun goToDefaultState() { callback.setView(REGISTRATION_STATE_DEFAULT) }

    override fun shouldWeGoToDefaultState() : Boolean {
        var gotoDefaultState = false
        viewModel.withState {
            gotoDefaultState = it.viewState == REGISTRATION_STATE_SIGNIN || it.viewState == REGISTRATION_STATE_LOGIN
        }
        return gotoDefaultState
    }

    interface CallbackFromViewModel{
        fun setView(state : Int)
        fun login(email : String,password : String)
        fun signIn(email: String)
        fun confirmEmail(code : String)
    }
}
interface CallbackToRegistrationActivity{
    fun openSaveInfoFragment(bundle : Bundle)
}
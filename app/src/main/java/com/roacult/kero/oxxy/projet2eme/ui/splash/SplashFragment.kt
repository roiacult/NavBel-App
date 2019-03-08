package com.roacult.kero.oxxy.projet2eme.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.MainActivity
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.RegistrationActivity
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.Fail

class SplashFragment : BaseFragment(){
    companion object { fun getInstance() = SplashFragment() }
    private val viewModel  : SplashViewModel by lazy { ViewModelProviders.of(this,viewModelFactory)[SplashViewModel::class.java]}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.observe(this){
            when(it.userStateOp){
                is Success<*> -> {
                    if(it.userStateOp.invoke() == true) goToMain()
                    else goToRegistration()
                }
                is Fail<*,*> ->{
                    //TODO show error in toast first
                    activity?.finish()}
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun goToRegistration() {
        startActivity(RegistrationActivity.getIntent(context!!))
        activity?.finish()
    }

    private fun goToMain() {
        startActivity(MainActivity.getIntent(context!!))
        activity?.finish()
    }
}
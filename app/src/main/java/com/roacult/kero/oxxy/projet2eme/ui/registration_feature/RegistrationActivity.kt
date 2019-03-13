package com.roacult.kero.oxxy.projet2eme.ui.registration_feature

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseActivity
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_saveinfo.SaveInfoFragment
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_signin_login.CallbackToRegistrationActivity
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_signin_login.RegistrationFragment
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.reset_password.ResetPasswordFragment
import com.roacult.kero.oxxy.projet2eme.utils.extension.inTransaction

class RegistrationActivity : BaseActivity() ,
    CallbackToRegistrationActivity {

    companion object {
        fun getIntent(context :Context) = Intent(context,RegistrationActivity::class.java)
    }

    private var callbackToFragment : CallbackToFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_fragment_activity)
        if(savedInstanceState == null) setFragment()
    }

    private fun setFragment() {
        val fragment = RegistrationFragment.getInstance()
        supportFragmentManager.inTransaction {
            add( R.id.fragment_container , fragment )
        }
    }

    private fun setUpCallback(){
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        callbackToFragment = if( fragment is ResetPasswordFragment)  fragment
        else if(fragment is RegistrationFragment ) fragment
        else null
    }

    override fun onBackPressed() {
        setUpCallback()
        if(callbackToFragment?.shouldWeGoToDefaultState() == true) callbackToFragment?.goToDefaultState()
        else super.onBackPressed()
    }

    override fun openSaveInfoFragment(bundle : Bundle){
        Log.v("sprint2","open save info (in activity)")
        supportFragmentManager.inTransaction {
            val fragment = SaveInfoFragment.getInstance()
            fragment.arguments = bundle
            setCustomAnimations ( R.anim.entre_from_right,R.anim.exit_to_left,R.anim.entre_from_left,R.anim.exit_to_right )
                .replace(R.id.fragment_container,fragment)
        }
    }

    override fun goToResetPassword() {
        Log.v("sprint2","open save info (in activity)")
        supportFragmentManager.inTransaction{
            val resetPasswordFragment = ResetPasswordFragment.getInstance()
            setCustomAnimations ( R.anim.entre_from_right,R.anim.exit_to_left,R.anim.entre_from_left,R.anim.exit_to_right )
                .addToBackStack(null)
                .add(R.id.fragment_container,resetPasswordFragment)
        }
    }

    interface CallbackToFragment{
        fun goToDefaultState()
        fun shouldWeGoToDefaultState() : Boolean
    }
}
package com.roacult.kero.oxxy.projet2eme.ui.registration_feature

import android.os.Bundle
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseActivity
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_saveinfo.SaveInfoFragment
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_signin_login.CallbackToRegistrationActivity
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_signin_login.RegistrationFragment
import com.roacult.kero.oxxy.projet2eme.utils.extension.inTransaction

class RegistrationActivity : BaseActivity() ,
    CallbackToRegistrationActivity {

    private var fragment : RegistrationFragment? = null
    private var callbackToFragment : CallbackToFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registartion_activity)
        if(savedInstanceState == null) setFragment()
    }

    private fun setFragment() {
        if(fragment == null) { fragment = RegistrationFragment.getInstance() }
        supportFragmentManager.inTransaction {
            add( R.id.registration_container , fragment!! )
        }
    }

    private fun setUpCallback(){
        if(fragment != null) {
            callbackToFragment = fragment
            return
        }
        fragment = supportFragmentManager.findFragmentById(R.id.registration_container) as? RegistrationFragment
        callbackToFragment = fragment
    }

    override fun onBackPressed() {
        setUpCallback()
        if(callbackToFragment?.shouldWeGoToDefaultState() == true) callbackToFragment?.goToDefaultState()
        else super.onBackPressed()
    }

    override fun openSaveInfoFragment(bundle : Bundle){
        supportFragmentManager.inTransaction {
            val fragment = SaveInfoFragment.getInstance()
            fragment.arguments = bundle
            replace(R.id.registration_container,fragment)
        }
    }

    interface CallbackToFragment{
        fun goToDefaultState()
        fun shouldWeGoToDefaultState() : Boolean
    }
}
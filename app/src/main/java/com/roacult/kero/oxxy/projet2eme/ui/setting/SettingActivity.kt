package com.roacult.kero.oxxy.projet2eme.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.roacult.kero.oxxy.domain.modules.User
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseActivity
import com.roacult.kero.oxxy.projet2eme.utils.extension.inTransaction

class SettingActivity : BaseActivity(){

    companion object {
        const val USER_INFO_FNAME = "com.roacult.kero.oxxy.projet2eme:FirestName"
        const val USER_INFO_LNAME = "com.roacult.kero.oxxy.projet2eme:LastName"
        const val USER_INFO_PICTURE = "com.roacult.kero.oxxy.projet2eme:Picture"
        const val USER_INFO_PUBLIC = "com.roacult.kero.oxxy.projet2eme:Public"


        fun getIntent(context : Context,user :User) =   Intent(context,SettingActivity::class.java).apply{
            val bundle = Bundle()
            bundle.putString(USER_INFO_PICTURE,user.picture)
            bundle.putString(USER_INFO_FNAME,user.fname)
            bundle.putString(USER_INFO_LNAME,user.lName)
            bundle.putBoolean(USER_INFO_PUBLIC,user.public)
            putExtras(bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_fragment_activity)

        if(savedInstanceState == null)
            supportFragmentManager.inTransaction {
                add(R.id.fragment_container,SettingFragment.getInstance(intent.extras!!))
            }
    }
}
package com.roacult.kero.oxxy.projet2eme.ui.creatpost

import android.os.Bundle
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseActivity
import com.roacult.kero.oxxy.projet2eme.utils.extension.inTransaction

class CreatPostActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.single_fragment_activity)

        if(savedInstanceState == null ){
            supportFragmentManager.inTransaction{
                add(R.id.fragment_container,CreatPostFragment.getInstance())
            }
        }
    }
}
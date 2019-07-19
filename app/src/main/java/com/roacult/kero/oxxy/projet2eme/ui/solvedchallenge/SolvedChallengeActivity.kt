package com.roacult.kero.oxxy.projet2eme.ui.solvedchallenge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.roacult.kero.oxxy.domain.modules.SolvedChalenge
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseActivity
import com.roacult.kero.oxxy.projet2eme.utils.extension.inTransaction

class SolvedChallengeActivity : BaseActivity() {

    companion object {
        const val ID = "solvedId"
        const val IMAGE_URL = "imageUrl"
        const val POINT = "point"
        const val POINT_PERCENT = "resultPoints"
        const val TIME_PERCENT = "timePercent"
        const val MODULES_NAME = "moduleName"


        fun getIntent(context : Context,result : SolvedChalenge) : Intent {
            val bundle = Bundle().apply {
                putLong(ID,result.id)
                putString(IMAGE_URL,result.imageUrl)
                putInt(POINT,result.resultPoints)
                putFloat(POINT_PERCENT,result.pointPercent)
                putFloat(TIME_PERCENT,result.timePercent)
                putString(MODULES_NAME,result.moduleName)
            }
            return Intent(context,SolvedChallengeActivity::class.java).apply {
                putExtras(bundle)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_fragment_activity)
        if(savedInstanceState == null) {
            supportFragmentManager.inTransaction{
                add(R.id.fragment_container,SolvedChallengeFragment.getInstance(intent.extras!!))
            }
        }
    }
}
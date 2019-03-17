package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseActivity
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.first_fragment.FirstFragment
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.second_fragment.SecondFragment
import com.roacult.kero.oxxy.projet2eme.utils.extension.inTransaction
import javax.inject.Inject

const val CHALENGE_MODULE ="com.roacult.kero.oxxy.projet2eme:chalengeModule"
const val CHALENGE_IMAGE ="com.roacult.kero.oxxy.projet2eme:chalengeImage"
const val CHALENGE_ID ="com.roacult.kero.oxxy.projet2eme:chalengeId"
const val CHALENGE_POINT ="com.roacult.kero.oxxy.projet2eme:chalengePoint"
const val CHALENGE_SOLVED ="com.roacult.kero.oxxy.projet2eme:chalengeSolved"
const val CHALENGE_QUESTION ="com.roacult.kero.oxxy.projet2eme:chalengeQuestion"
const val CHALENGE_STORY ="com.roacult.kero.oxxy.projet2eme:chalengeStory"


class StartChalengeActivity : BaseActivity(){

    companion object {
        fun getIntent(context : Context,chalengeGlobale: ChalengeGlobale): Intent {
            val intent = Intent(context,StartChalengeActivity::class.java)
            val bundle = Bundle()
            bundle.putInt(CHALENGE_ID,chalengeGlobale.id)
            bundle.putInt(CHALENGE_POINT,chalengeGlobale.point)
            bundle.putInt(CHALENGE_SOLVED,chalengeGlobale.nbPersonSolved)
            bundle.putInt(CHALENGE_QUESTION,chalengeGlobale.nbOfQuestions)
            bundle.putString(CHALENGE_MODULE,chalengeGlobale.module)
            bundle.putString(CHALENGE_STORY,chalengeGlobale.story)
            bundle.putString(CHALENGE_IMAGE,chalengeGlobale.url)
            intent.putExtras(bundle)
            return intent
        }
    }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel : StartChelngeViewModel by lazy { ViewModelProviders.of(this,viewModelFactory)[StartChelngeViewModel::class.java]}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_fragment_activity)
        viewModel.observe(this){
            it.selectedFragment.getContentIfNotHandled()?.apply { loadFragment(this) }
        }

    }

    private fun loadFragment(fra : Int) {
        when(fra){
            STARTCHALENGE_FRAGMENT1 -> supportFragmentManager.inTransaction{
                val fragment = FirstFragment.getInstance()
                fragment.arguments = intent.extras
                add(R.id.fragment_container,fragment)
            }

            STARTCHALENGE_FRAGMENT2 ->supportFragmentManager.inTransaction{
                setCustomAnimations ( R.anim.entre_from_right,R.anim.exit_to_left,R.anim.entre_from_left,R.anim.exit_to_right )
                replace(R.id.fragment_container,SecondFragment.getInstance())
            }
        }
    }
}
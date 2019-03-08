package com.roacult.kero.oxxy.projet2eme.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseActivity
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.award_fragment.AwardFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.chalenge_fragment.ChalengeFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.forume_fragment.ForumeFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.profile_fragment.ProfileFragment
import kotlinx.android.synthetic.main.main_activity.*
import com.roacult.kero.oxxy.projet2eme.utils.extension.inTransaction

const val CURENT_FRAGMET = "curent_fragment"

class MainActivity : BaseActivity() {

    companion object { fun getIntent(context : Context) = Intent(context,MainActivity::class.java) }
    private val chalangeFragment = ChalengeFragment.getInstance()
    private val forumFragment = ForumeFragment.getInstance()
    private val awardFragment = AwardFragment.getInstance()
    private val profileFragment = ProfileFragment.getInstance()

    private var selectedFragment :Int = R.id.chalenge_page

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        savedInstanceState?.let { selectedFragment = it.getInt(CURENT_FRAGMET) }
        bottom_nav.setOnNavigationItemSelectedListener { setFragment(it.itemId) }
        bottom_nav.selectedItemId = selectedFragment
    }

    private fun setFragment(itemId: Int) : Boolean {

        val curentFragment = supportFragmentManager.findFragmentById(R.id.main_container)
        selectedFragment = itemId
        when(itemId){
            R.id.chalenge_page -> {
                if(curentFragment is ChalengeFragment) return false
                supportFragmentManager.inTransaction{replace(R.id.main_container,chalangeFragment) }
            }
            R.id.forum_page ->{
                if(curentFragment is ForumeFragment) return false
                supportFragmentManager.inTransaction{replace(R.id.main_container,forumFragment) }
            }
            R.id.award_page ->{
                if(curentFragment is AwardFragment) return false
                supportFragmentManager.inTransaction{replace(R.id.main_container,awardFragment) }
            }
            R.id.profile_page ->{
                if(curentFragment is ProfileFragment) return false
                supportFragmentManager.popBackStack()
                supportFragmentManager.inTransaction{replace(R.id.main_container,profileFragment)}
            }
            else -> return false
        }

        return true
    }

    override fun onBackPressed() {
        if(selectedFragment != R.id.chalenge_page) bottom_nav.selectedItemId = R.id.chalenge_page
        else super.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(CURENT_FRAGMET,bottom_nav.selectedItemId)
    }
}
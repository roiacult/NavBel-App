package com.roacult.kero.oxxy.projet2eme.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
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
    private var callback  : CallbackFromActivity = chalangeFragment

    private var selectedFragment :Int = R.id.chalenge_page

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
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
                callback = chalangeFragment
                invalidateOptionsMenu()
            }
            R.id.forum_page ->{
                if(curentFragment is ForumeFragment) return false
                supportFragmentManager.inTransaction{replace(R.id.main_container,forumFragment) }
                callback = forumFragment
                invalidateOptionsMenu()
            }
            R.id.award_page ->{
                if(curentFragment is AwardFragment) return false
                supportFragmentManager.inTransaction{replace(R.id.main_container,awardFragment) }
                callback = awardFragment
                invalidateOptionsMenu()
            }
            R.id.profile_page ->{
                if(curentFragment is ProfileFragment) return false
                supportFragmentManager.popBackStack()
                supportFragmentManager.inTransaction{replace(R.id.main_container,profileFragment)}
                callback  = profileFragment
                invalidateOptionsMenu()
            }
            else -> return false
        }

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        when (selectedFragment) {
            R.id.chalenge_page -> {
                menu?.findItem(R.id.filter)?.isVisible= true
                invalidateOptionsMenu()
            }
            R.id.forum_page -> {
                menu?.findItem(R.id.filter)?.isVisible= false
                invalidateOptionsMenu()
            }
            R.id.award_page -> {
                menu?.findItem(R.id.filter)?.isVisible= false
                invalidateOptionsMenu()
            }
            R.id.profile_page -> {
                menu?.findItem(R.id.filter)?.isVisible= false
                invalidateOptionsMenu()
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chalenge_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.help_menu -> showHelp()
            R.id.filter -> callback.showFilter()
        }
        return true
    }

    private fun showHelp() {
        TapTargetSequence(this).apply {
            target(TapTarget.forView(bottom_nav.findViewById(R.id.chalenge_page),getString(R.string.help_chalenge),getString(R.string.help_chalenge_des)))
            target(TapTarget.forToolbarMenuItem(toolbar,R.id.filter,getString(R.string.help_filter),getString(R.string.help_filter_des)))
        }.listener(object : TapTargetSequence.Listener{
            override fun onSequenceCanceled(lastTarget: TapTarget?) {}
            override fun onSequenceFinish() { callback.showHelp() }
            override fun onSequenceStep(lastTarget: TapTarget?, targetClicked: Boolean) {}
        }).start()
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
interface CallbackFromActivity{
    fun showHelp()
    fun showFilter()
}
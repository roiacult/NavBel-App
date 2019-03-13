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
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.award_fragment.AwardFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.chalenge_fragment.ChalengeFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.forume_fragment.ForumeFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.profile_fragment.ProfileFragment
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.StartChalengeActivity
import kotlinx.android.synthetic.main.main_activity.*
import com.roacult.kero.oxxy.projet2eme.utils.extension.inTransaction

const val CURENT_FRAGMET = "com.roacult.kero.oxxy.projet2eme:curent_fragment"

const val TAG_CHALENGE = "com.roacult.kero.oxxy.projet2eme:chalenge"
const val TAG_FORUME = "com.roacult.kero.oxxy.projet2eme:forume"
const val TAG_AWARD = "com.roacult.kero.oxxy.projet2eme:award"
const val TAG_PROFILE = "com.roacult.kero.oxxy.projet2eme:profile"

class MainActivity : BaseActivity() {

    companion object { fun getIntent(context : Context) = Intent(context,MainActivity::class.java) }

    private val chalengeFragment : ChalengeFragment by lazy {
        val fr = supportFragmentManager.findFragmentByTag(TAG_CHALENGE)
        if(fr != null)fr as ChalengeFragment
        else ChalengeFragment.getInstance()
    }
    private val forumFragment  : ForumeFragment by lazy {
        val fr = supportFragmentManager.findFragmentByTag(TAG_FORUME)
        if(fr != null) fr as ForumeFragment
        else ForumeFragment.getInstance()
    }
    private val awardFragment : AwardFragment by lazy {
        val fr = supportFragmentManager.findFragmentByTag(TAG_AWARD)
        if(fr != null) fr as AwardFragment
        else AwardFragment.getInstance()
    }
    private val profileFragment : ProfileFragment by lazy {
        val fr = supportFragmentManager.findFragmentByTag(TAG_PROFILE)
        if(fr != null) fr as ProfileFragment
        else ProfileFragment.getInstance()
    }

    private var activeFragment  : BaseFragment? = null

    private var callback  : CallbackFromActivity? = null

    private var selectedFragment :Int = R.id.chalenge_page

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        savedInstanceState?.let { selectedFragment = it.getInt(CURENT_FRAGMET) }
        initialze(savedInstanceState == null)
    }

    private fun initialze(addFragments : Boolean) {
        setSupportActionBar(toolbar)
        //set active fragment
        when(selectedFragment){
            R.id.chalenge_page -> {
                activeFragment = chalengeFragment
                callback = chalengeFragment
            }
            R.id.forum_page -> {
                activeFragment = forumFragment
                callback =forumFragment
            }
            R.id.award_page -> {
                activeFragment = awardFragment
                callback = awardFragment
            }
            R.id.profile_page -> {
                activeFragment = profileFragment
                callback = profileFragment
            }
        }

        //add all fragment but only show selected fragment
        if(addFragments)supportFragmentManager.inTransaction{
            add(R.id.main_container,chalengeFragment, TAG_CHALENGE).hide(chalengeFragment)
            add(R.id.main_container,forumFragment, TAG_FORUME).hide(forumFragment)
            add(R.id.main_container,awardFragment, TAG_AWARD).hide(awardFragment)
            add(R.id.main_container,profileFragment , TAG_PROFILE).hide(profileFragment)
            show(activeFragment!!)
        }

        bottom_nav.setOnNavigationItemSelectedListener { setFragment(it.itemId) }
        bottom_nav.selectedItemId = selectedFragment
    }

    private fun setFragment(itemId: Int) : Boolean {
        selectedFragment = itemId
        when(itemId){
            R.id.chalenge_page -> {
                if(activeFragment is ChalengeFragment) return false
                supportFragmentManager.inTransaction{
                    hide(activeFragment!!).show(chalengeFragment).attach(chalengeFragment)
                }
                activeFragment = chalengeFragment
                callback = chalengeFragment
            }
            R.id.forum_page ->{
                if(activeFragment is ForumeFragment) return false
                supportFragmentManager.inTransaction{
                    hide(activeFragment!!).show(forumFragment).attach(chalengeFragment)
                }
                activeFragment = forumFragment
                callback = forumFragment
            }
            R.id.award_page ->{
                if(activeFragment is AwardFragment) return false
                supportFragmentManager.inTransaction{
                    hide(activeFragment!!).show(awardFragment).attach(chalengeFragment)
                }
                activeFragment = awardFragment
                callback = awardFragment
            }
            R.id.profile_page ->{
                if(activeFragment is ProfileFragment) return false
                supportFragmentManager.inTransaction{
                    hide(activeFragment!!).show(profileFragment).attach(chalengeFragment)
                }
                activeFragment = profileFragment
                callback = profileFragment
            }
            else -> return false
        }
        invalidateOptionsMenu()
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        when (selectedFragment) {
            R.id.chalenge_page -> {
                menu?.findItem(R.id.filter)?.isVisible= true
            }
            R.id.forum_page -> {
                menu?.findItem(R.id.filter)?.isVisible= false
            }
            R.id.award_page -> {
                menu?.findItem(R.id.filter)?.isVisible= false
            }
            R.id.profile_page -> {
                menu?.findItem(R.id.filter)?.isVisible= false
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
            R.id.filter -> callback?.showFilter()
        }
        return true
    }

    private fun showHelp() {
        TapTargetSequence(this).apply {
            val (title : String, desc :String) = when(selectedFragment){
                R.id.chalenge_page -> Pair(getString(R.string.help_chalenge),getString(R.string.help_chalenge_des))
                R.id.forum_page -> Pair(getString(R.string.help_forum),getString(R.string.help_forum_des))
                R.id.award_page -> Pair(getString(R.string.help_award),getString(R.string.help_award_des))
                R.id.profile_page -> Pair(getString(R.string.help_profile),getString(R.string.help_profile_des))
                else -> Pair("","")
            }
            target(TapTarget.forView(bottom_nav.findViewById(selectedFragment),title,desc))
            if(selectedFragment == R.id.chalenge_page)target(TapTarget.forToolbarMenuItem(toolbar,R.id.filter,getString(R.string.help_filter),getString(R.string.help_filter_des)))
        }.listener(object : TapTargetSequence.Listener{
            override fun onSequenceCanceled(lastTarget: TapTarget?) {}
            override fun onSequenceFinish() {
                callback?.showHelp() }
            override fun onSequenceStep(lastTarget: TapTarget?, targetClicked: Boolean) {}
        }).start()
    }

    fun startChalenge(bundle: Bundle){
        val intent = StartChalengeActivity.getIntent(this)
        intent.putExtras(bundle)
        startActivity(intent)
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
package com.roacult.kero.oxxy.projet2eme.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.iammert.library.readablebottombar.ReadableBottomBar
import com.roacult.kero.oxxy.domain.interactors.LogeOut
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseActivity
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.award_fragment.AwardFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.chalenge_fragment.ChalengeFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.forume_fragment.ForumeFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.profile_fragment.ProfileFragment
import com.roacult.kero.oxxy.projet2eme.ui.registration_feature.RegistrationActivity
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.StartChalengeActivity
import kotlinx.android.synthetic.main.main_activity.*
import com.roacult.kero.oxxy.projet2eme.utils.extension.inTransaction
import javax.inject.Inject

const val CURENT_FRAGMET = "com.roacult.kero.oxxy.projet2eme:curent_fragment"

const val TAG_CHALENGE = "com.roacult.kero.oxxy.projet2eme:chalenge"
const val TAG_FORUME = "com.roacult.kero.oxxy.projet2eme:forume"
const val TAG_AWARD = "com.roacult.kero.oxxy.projet2eme:award"
const val TAG_PROFILE = "com.roacult.kero.oxxy.projet2eme:profile"

class MainActivity : BaseActivity() {
    @Inject
    lateinit var logOutUseCase :LogeOut
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

    private var selectedFragment :Int = 0

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
            0 -> {
                activeFragment = chalengeFragment
                callback = chalengeFragment
            }
            1 -> {
                activeFragment = forumFragment
                callback =forumFragment
            }
            2 -> {
                activeFragment = awardFragment
                callback = awardFragment
            }
            3 -> {
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

        bottom_nav.setOnItemSelectListener ( object : ReadableBottomBar.ItemSelectListener{
            override fun onItemSelected(index: Int) {setFragment(index)}
        })
        bottom_nav.setSelectedItem(selectedFragment)
    }

    private fun setFragment(position: Int)  {
        selectedFragment = position
        when(position){
            0 -> {
                if(activeFragment is ChalengeFragment) return
                supportFragmentManager.inTransaction{
                    hide(activeFragment!!).show(chalengeFragment).attach(chalengeFragment)
                }
                activeFragment = chalengeFragment
                callback = chalengeFragment
                supportActionBar?.show()
            }
            1 ->{
                if(activeFragment is ForumeFragment) return
                supportFragmentManager.inTransaction{
                    hide(activeFragment!!).show(forumFragment).attach(forumFragment)
                }
                forumFragment.refresh()
                activeFragment = forumFragment
                callback = forumFragment
                supportActionBar?.show()
            }
            2 ->{
                if(activeFragment is AwardFragment) return
                supportFragmentManager.inTransaction{
                    hide(activeFragment!!).show(awardFragment).attach(awardFragment)
                }
                activeFragment = awardFragment
                callback = awardFragment
                supportActionBar?.show()
            }
            3 ->{
                if(activeFragment is ProfileFragment) return
                supportFragmentManager.inTransaction{
                    hide(activeFragment!!).show(profileFragment).attach(profileFragment)
                }
                activeFragment = profileFragment
                callback = profileFragment
                supportActionBar?.hide()
            }
        }
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        when (selectedFragment) {
            0 -> {
                menu?.findItem(R.id.filter)?.isVisible= true
                menu?.findItem(R.id.log_out)?.isVisible = true
                menu?.findItem(R.id.add_post)?.isVisible = false
            }
            1 -> {
                menu?.findItem(R.id.filter)?.isVisible= false
                menu?.findItem(R.id.log_out)?.isVisible = false
                menu?.findItem(R.id.add_post)?.isVisible = true
            }
            2 -> {
                menu?.findItem(R.id.filter)?.isVisible= false
                menu?.findItem(R.id.log_out)?.isVisible = false
                menu?.findItem(R.id.add_post)?.isVisible = false
            }
            3 -> {
                menu?.findItem(R.id.filter)?.isVisible= false
                menu?.findItem(R.id.log_out)?.isVisible = false
                menu?.findItem(R.id.add_post)?.isVisible = false
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
            R.id.log_out ->{
                logOutUseCase.invoke()
                startActivity(RegistrationActivity.getIntent(this))
                finish()
            }
            R.id.add_post -> {
                forumFragment.addPost()
            }
        }
        return true
    }

    fun showHelp() {
        TapTargetSequence(this).apply {
            val (title : String, desc :String) = when(selectedFragment){
                0 -> Pair(getString(R.string.help_chalenge),getString(R.string.help_chalenge_des))
                1 -> Pair(getString(R.string.help_forum),getString(R.string.help_forum_des))
                2 -> Pair(getString(R.string.help_award),getString(R.string.help_award_des))
                3 -> Pair(getString(R.string.help_profile),getString(R.string.help_profile_des))
                else -> Pair("","")
            }
            target(TapTarget.forView(bottom_nav.getChildViewAt(selectedFragment)!!.textView,title,desc))
            if(selectedFragment == 0)target(TapTarget.forToolbarMenuItem(toolbar,R.id.filter,getString(R.string.help_filter),getString(R.string.help_filter_des)))
        }.listener(object : TapTargetSequence.Listener{
            override fun onSequenceCanceled(lastTarget: TapTarget?) {}
            override fun onSequenceFinish() {
                callback?.showHelp() }
            override fun onSequenceStep(lastTarget: TapTarget?, targetClicked: Boolean) {}
        }).start()
    }

    fun setSelectedItem(position: Int){
        bottom_nav.setSelectedItem(position)
    }

    override fun onBackPressed() {
        if(selectedFragment != 0) bottom_nav.setSelectedItem(0)
        else super.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(CURENT_FRAGMET,selectedFragment)
    }
}
interface CallbackFromActivity{
    fun showHelp()
    fun showFilter()
}
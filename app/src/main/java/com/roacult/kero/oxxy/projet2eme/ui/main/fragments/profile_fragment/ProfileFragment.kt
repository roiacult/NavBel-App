package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.profile_fragment

import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.CallbackFromActivity

class ProfileFragment : BaseFragment() ,CallbackFromActivity {
    companion object { fun getInstance() = ProfileFragment() }

    override fun showHelp() {}

    override fun showFilter() {}
}
package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.award_fragment

import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.CallbackFromActivity

class AwardFragment  : BaseFragment() , CallbackFromActivity {
    companion object { fun getInstance() = AwardFragment() }

    override fun showHelp() {}

    override fun showFilter() {}
}
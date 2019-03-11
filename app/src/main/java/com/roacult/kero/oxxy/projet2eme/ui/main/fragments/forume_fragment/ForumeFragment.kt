package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.forume_fragment

import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.CallbackFromActivity

class ForumeFragment  : BaseFragment() , CallbackFromActivity{
    companion object { fun getInstance() = ForumeFragment() }

    override fun showHelp() {}

    override fun showFilter() {}
}
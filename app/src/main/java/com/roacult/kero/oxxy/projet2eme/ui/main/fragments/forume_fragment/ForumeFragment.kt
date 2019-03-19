package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.forume_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.ui.main.CallbackFromActivity

class ForumeFragment  : BaseFragment(),CallbackFromActivity{
    companion object { fun getInstance() = ForumeFragment() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tmp,container,false)
    }

    override fun showHelp() {}

    override fun showFilter() {}
}
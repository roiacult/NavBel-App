package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_saveinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment

const val SAVEINFO_FIRST_NAME = "first_name"
const val SAVEINFO_LAST_NAME = "last_name"
const val SAVEINFO_YEAR = "year"

class SaveInfoFragment : BaseFragment() {

    companion object {fun getInstance() = SaveInfoFragment()}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}

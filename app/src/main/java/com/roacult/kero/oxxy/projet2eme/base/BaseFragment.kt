package com.roacult.kero.oxxy.projet2eme.base

import androidx.lifecycle.ViewModelProvider
import android.content.Context
import dagger.android.support.DaggerFragment
import javax.inject.Inject

open class BaseFragment :DaggerFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
     interface BaseCallBack
    protected var mActivity: BaseActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            mActivity = context

        }
    }
    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }
}
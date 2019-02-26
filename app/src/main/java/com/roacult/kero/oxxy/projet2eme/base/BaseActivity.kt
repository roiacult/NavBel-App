package com.roacult.kero.oxxy.projet2eme.base

import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    abstract fun fragment(): BaseFragment
}
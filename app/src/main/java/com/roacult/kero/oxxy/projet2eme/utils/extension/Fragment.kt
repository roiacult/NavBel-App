
package com.roacult.kero.oxxy.projet2eme.utils.extension

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment


inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
        beginTransaction().func().commit()

inline fun <reified T : ViewModel> Fragment.viewModel(factory: ViewModelProvider.Factory, body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}

fun BaseFragment.close() = fragmentManager?.popBackStack()
val BaseFragment.appContext: Context get() = activity?.applicationContext!!
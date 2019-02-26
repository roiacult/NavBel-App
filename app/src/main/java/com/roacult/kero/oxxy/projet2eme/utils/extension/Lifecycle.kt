
package com.roacult.kero.oxxy.projet2eme.utils.extension


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.roacult.kero.oxxy.domain.exception.Failure


fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
        liveData.observe(this, Observer(body))

fun <L : LiveData<Failure>> LifecycleOwner.failure(liveData: L, body: (Failure?) -> Unit) =
        liveData.observe(this, Observer(body))
package com.roacult.kero.oxxy.projet2eme.utils.schedulers

import com.roacult.kero.oxxy.domain.functional.AppRxSchedulers
import io.reactivex.Scheduler

data class AppRxSchedulersImpl(override val io :Scheduler, override val main:Scheduler, override val computation:Scheduler ):
    AppRxSchedulers
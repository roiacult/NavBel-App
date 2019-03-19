package com.roacult.kero.oxxy.domain.functional

import io.reactivex.Scheduler

interface AppRxSchedulers {
     val io : Scheduler
     val main :Scheduler
    val computation:Scheduler
}
package com.roacult.kero.oxxy.domain.functional

import kotlinx.coroutines.CoroutineDispatcher

interface CouroutineDispatchers {
    val io:CoroutineDispatcher
    val main:CoroutineDispatcher
    val computaion :CoroutineDispatcher
}
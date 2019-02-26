package com.roacult.kero.oxxy.projet2eme.utils.Schedulers

import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import kotlinx.coroutines.CoroutineDispatcher

class AppCoroutineDispatchers(override val io: CoroutineDispatcher, override val computaion: CoroutineDispatcher,
                              override val main: CoroutineDispatcher): CouroutineDispatchers
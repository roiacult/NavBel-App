package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers

class LogeOut(dispatchers : CouroutineDispatchers) : Interactor<None,None>{
    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main
    override suspend fun invoke(executeParams: None): None {
        //TODO i don't want this functions to be suspended
        // change it if you can
        return None()
    }
}
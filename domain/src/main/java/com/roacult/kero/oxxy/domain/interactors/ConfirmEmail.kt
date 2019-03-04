package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import kotlinx.coroutines.delay
import javax.inject.Inject

class ConfirmEmail @Inject constructor(dispatchers : CouroutineDispatchers) : EitherInteractor<String,None,Failure.ConfirmEmailFaillure>  {
    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: String): Either<Failure.ConfirmEmailFaillure, None> {
        delay(5000)
        return Either.Right(None())
    }
}
package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import kotlinx.coroutines.delay
import javax.inject.Inject

/*
* use case for resending email
* */
class ResendConfirmationCode  @Inject constructor(dispatchers: CouroutineDispatchers): EitherInteractor<None,None,Failure> {
    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: None): Either<Failure, None> {
        delay(3000)
        return Either.Right(None())
    }
}
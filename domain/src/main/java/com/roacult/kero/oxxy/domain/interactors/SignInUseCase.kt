package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import javax.inject.Inject

class SignInUseCase @Inject constructor(val dispatchers: CouroutineDispatchers) : EitherInteractor<String,None,Failure.SignInFaillure> {

    override val dispatcher: CoroutineDispatcher
        get() = dispatchers.computaion
    override val ResultDispatcher: CoroutineDispatcher
        get() = dispatchers.main

    override suspend fun invoke(executeParams: String): Either<Failure.SignInFaillure, None> {
        //just for testing
        delay(5000)
        return Either.Right(None())
    }
}

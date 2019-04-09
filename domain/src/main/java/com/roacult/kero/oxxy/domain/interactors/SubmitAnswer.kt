package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import kotlinx.coroutines.delay
import javax.inject.Inject

class SubmitAnswer @Inject constructor(dispatchers: CouroutineDispatchers) : EitherInteractor<Map<Long,Long>,SubmitionResult, Failure.SubmitionFailure> {
    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: Map<Long, Long>): Either<Failure.SubmitionFailure, SubmitionResult> {
        delay(3000)
        return Either.Right(SubmitionResult(true,1500))
    }
}

data class SubmitionResult(val success : Boolean, val points : Long)
package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import kotlinx.coroutines.delay
import javax.inject.Inject

class SubmitAnswer @Inject constructor(dispatchers: CouroutineDispatchers) : EitherInteractor<SubmitionParam,SubmitionResult, Failure.SubmitionFailure> {
    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: SubmitionParam): Either<Failure.SubmitionFailure, SubmitionResult> {
        delay(3000)
        return Either.Right(SubmitionResult(true,80))
    }
}
data class SubmitionParam(val answers : Map<Long,Long>,val time : Float,val chalengeId : Int)
data class SubmitionResult(val success : Boolean, val points : Long)
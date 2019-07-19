package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * i will invoke this use case when ever user start chalenge
 * just insert user id and chalnge id in try table
 * */


class SetUserTry @Inject constructor(dispatchers: CouroutineDispatchers , val repo:MainRepository)
    : EitherInteractor<Int,Map<Long,Long>,Failure.UserTryFailure>{
    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: Int): Either<Failure.UserTryFailure, Map<Long,Long>> {

//
//
//        delay(3000)
//        return Either.Right(mutableMapOf(0L to 1L,1L to 4L))
        return repo.setUserTry(executeParams)
    }
}

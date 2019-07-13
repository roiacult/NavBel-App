package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * this use case will be invoked when user want to exchange his
 * points and purchase reward
 * i will pass award id and you should do a request to server if
 * operation goes well server will send email to user contain
 * details of award like gift card code or netflix account information ...
 * */

class GetGift @Inject constructor(couroutineDispatchers: CouroutineDispatchers, val repository: MainRepository) : EitherInteractor<Int,None, Failure.GetGift> {

    override val dispatcher =couroutineDispatchers.computaion
    override val ResultDispatcher= couroutineDispatchers.main

    override suspend fun invoke(executeParams: Int): Either<Failure.GetGift, None> {
//        //TODO
//        delay(3000)
//        return Either.Right(None())
        return repository.getAward(executeParams)
    }
}
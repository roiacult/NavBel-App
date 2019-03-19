package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import javax.inject.Inject



class SetUserTry @Inject constructor(dispatchers: CouroutineDispatchers , val repo:MainRepository): EitherInteractor<Int,None,Failure.UserTryFailure>{
    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: Int): Either<Failure.UserTryFailure, None> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

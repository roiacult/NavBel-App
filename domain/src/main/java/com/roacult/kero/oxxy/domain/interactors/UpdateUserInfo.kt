package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.User
import kotlinx.coroutines.delay
import javax.inject.Inject

class UpdateUserInfo @Inject constructor(couroutineDispatchers: CouroutineDispatchers) : EitherInteractor<User,None, Failure.UpDateUserInfo> {

    override val dispatcher =couroutineDispatchers.computaion
    override val ResultDispatcher= couroutineDispatchers.main

    override suspend fun invoke(executeParams: User): Either<Failure.UpDateUserInfo, None> {
        delay(3000)
        return Either.Right(None())
    }
}
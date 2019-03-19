package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.AuthentificationRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import javax.inject.Inject

class ResendConfirmationCode @Inject constructor(dispatchers: CouroutineDispatchers ,
                                                 val repo:AuthentificationRepository) :EitherInteractor<String , None,
        Failure.ResendConfirmationFailure > {
    override val dispatcher= dispatchers.io
    override val ResultDispatcher=dispatchers.main

    override suspend fun invoke(executeParams: String): Either<Failure.ResendConfirmationFailure, None> {
       return repo.resendConfirmationCode(executeParams)
//        delay(3000)
//        return Either.Right(None())
    }
}
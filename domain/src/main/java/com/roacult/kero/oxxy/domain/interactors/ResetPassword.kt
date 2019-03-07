package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.AuthentificationRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import javax.inject.Inject

class ResetPassword @Inject constructor(val repo:AuthentificationRepository,
                                        dispatchers: CouroutineDispatchers): EitherInteractor<ResetPasswordParams , None , Failure.ResetPasswordFailure> {
    override val dispatcher = dispatchers.io
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: ResetPasswordParams): Either<Failure.ResetPasswordFailure, None> {
//     return repo.resetPassword(executeParams)
        delay(3000)
        return Either.Right(None())
    }
}
data class ResetPasswordParams(val email:String , val password:String)
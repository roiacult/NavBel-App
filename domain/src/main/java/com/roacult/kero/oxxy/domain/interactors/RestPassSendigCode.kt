package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.AuthentificationRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import kotlinx.coroutines.delay
import javax.inject.Inject

class RestPassSendigCode  @Inject constructor(dispatchers : CouroutineDispatchers ,
                                              val repo:AuthentificationRepository) : EitherInteractor<String,None,
        Failure.SendCodeResetPassword> {
    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: String): Either<Failure.SendCodeResetPassword, None> {
      return repo.sendCodeResetPass(executeParams)
    }
}
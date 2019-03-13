package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.ChalengeDetailles
import kotlinx.coroutines.delay

class GetChalengeDetaills(dispatchers: CouroutineDispatchers , val repo:MainRepository) : EitherInteractor<Int,ChalengeDetailles
        ,Failure.GetChalengeDetailsFailure> {
    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: Int): Either<Failure.GetChalengeDetailsFailure, ChalengeDetailles> {
      return repo.getChallengeDetaille(executeParams)
    }
}
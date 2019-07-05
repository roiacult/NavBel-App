package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import javax.inject.Inject

class SolvedChalenge @Inject constructor(couroutineDispatchers: CouroutineDispatchers) :
        EitherInteractor<None,List<SolvedChalenge>,Failure.SolvedChalengeFailure> {

    override val dispatcher =couroutineDispatchers.computaion
    override val ResultDispatcher= couroutineDispatchers.main

    override suspend fun invoke(executeParams: None): Either<Failure.SolvedChalengeFailure, List<SolvedChalenge>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
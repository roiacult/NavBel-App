package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import kotlinx.coroutines.CoroutineDispatcher

class GetAllChallenges:EitherInteractor<None , List<ChalengeGlobale> ,Failure.GetAllChalengesFailure> {
    override val dispatcher: CoroutineDispatcher
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val ResultDispatcher: CoroutineDispatcher
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override suspend fun invoke(executeParams: None): Either<Failure.GetAllChalengesFailure, List<ChalengeGlobale>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
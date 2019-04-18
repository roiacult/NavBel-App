package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.Award
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetAwards @Inject constructor(dispatchers: CouroutineDispatchers)  : EitherInteractor<None,List<Award>,Failure.GetAwardsFailure>{
    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: None): Either<Failure.GetAwardsFailure, List<Award>> {

        delay(3000)
        val list = ArrayList<Award>()
        list.add(Award("001","https://i.ibb.co/34BmB25/award1.png",1200,"blabla bla bla"))
        list.add(Award("002","https://i.ibb.co/W6tFdMG/award2.png",500,"bla bka ba njhbhjbc"))
        return Either.Right(list)
    }
}
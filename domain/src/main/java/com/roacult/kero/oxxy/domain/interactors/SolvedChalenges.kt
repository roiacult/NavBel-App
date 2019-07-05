package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.SolvedChalenge
import kotlinx.coroutines.delay
import javax.inject.Inject

class SolvedChalenges @Inject constructor(couroutineDispatchers: CouroutineDispatchers) :
        EitherInteractor<None,List<SolvedChalenge>,Failure.SolvedChalengeFailure> {

    override val dispatcher =couroutineDispatchers.computaion
    override val ResultDispatcher= couroutineDispatchers.main

    override suspend fun invoke(executeParams: None): Either<Failure.SolvedChalengeFailure, List<SolvedChalenge>> {
        delay(3000)
        val list = ArrayList<SolvedChalenge>()
        list.add(SolvedChalenge(0L,"https://fr.myposeo.com/blog/wp-content/uploads/2016/02/analyse-visibilite-ecommerce-france.png",120,50))
        list.add(SolvedChalenge(1L,"https://fr.myposeo.com/blog/wp-content/uploads/2016/02/analyse-visibilite-ecommerce-france.png",150,0))
        list.add(SolvedChalenge(2L,"https://fr.myposeo.com/blog/wp-content/uploads/2016/02/analyse-visibilite-ecommerce-france.png",180,0))
        list.add(SolvedChalenge(3L,"https://fr.myposeo.com/blog/wp-content/uploads/2016/02/analyse-visibilite-ecommerce-france.png",80,70))
        list.add(SolvedChalenge(4L,"https://fr.myposeo.com/blog/wp-content/uploads/2016/02/analyse-visibilite-ecommerce-france.png",110,40))
        return Either.Right(list)
    }
}
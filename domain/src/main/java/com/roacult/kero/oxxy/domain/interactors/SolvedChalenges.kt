package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.SolvedChalenge
import kotlinx.coroutines.delay
import javax.inject.Inject

class SolvedChalenges @Inject constructor(couroutineDispatchers: CouroutineDispatchers , private val  repository: MainRepository) :
        EitherInteractor<None,List<SolvedChalenge>,Failure.SolvedChalengeFailure> {

    override val dispatcher =couroutineDispatchers.computaion
    override val ResultDispatcher= couroutineDispatchers.main

    override suspend fun invoke(executeParams: None): Either<Failure.SolvedChalengeFailure, List<SolvedChalenge>> {
//        delay(3000)
//        val list = ArrayList<SolvedChalenge>()
//        list.add(SolvedChalenge(0L,"https://fr.myposeo.com/blog/wp-content/uploads/2016/02/analyse-visibilite-ecommerce-france.png",120,0.6F,0.8F,"Annalyse"))
//        list.add(SolvedChalenge(1L,"https://fr.myposeo.com/blog/wp-content/uploads/2016/02/analyse-visibilite-ecommerce-france.png",150,0F,0.5F,"Annalyse"))
//        list.add(SolvedChalenge(2L,"https://fr.myposeo.com/blog/wp-content/uploads/2016/02/analyse-visibilite-ecommerce-france.png",0,0F,0F,"Algebre"))
//        list.add(SolvedChalenge(3L,"https://fr.myposeo.com/blog/wp-content/uploads/2016/02/analyse-visibilite-ecommerce-france.png",80,0F,0.9F,"ISI"))
//        list.add(SolvedChalenge(4L,"https://fr.myposeo.com/blog/wp-content/uploads/2016/02/analyse-visibilite-ecommerce-france.png",0,0F,0F,"POO"))
//        return Either.Right(list)
        return repository.getSolvedChallenge()
    }
}
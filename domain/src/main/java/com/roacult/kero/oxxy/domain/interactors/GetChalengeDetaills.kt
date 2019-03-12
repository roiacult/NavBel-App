package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.ChalengeDetailles
import kotlinx.coroutines.delay

class GetChalengeDetaills(dispatchers: CouroutineDispatchers) : EitherInteractor<Int,ChalengeDetailles
        ,Failure.GetChalengeDetailsFailure> {
    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: Int): Either<Failure.GetChalengeDetailsFailure, ChalengeDetailles> {
        delay(3000)
        val list = ArrayList<Pair<String,String>>()
        list.add(Pair("res1","https:url1"))
        list.add(Pair("res2","https:url2"))
        list.add(Pair("res3","https:url3"))
        list.add(Pair("res4","https:url4"))
        return Either.Right(ChalengeDetailles(0,1200,list,ArrayList()))
    }
}
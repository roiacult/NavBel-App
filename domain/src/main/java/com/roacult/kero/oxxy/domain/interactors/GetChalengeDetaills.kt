package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.ChalengeDetailles
import com.roacult.kero.oxxy.domain.modules.Question
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetChalengeDetaills @Inject constructor(dispatchers: CouroutineDispatchers, val repo:MainRepository) : EitherInteractor<Int,ChalengeDetailles
        ,Failure.GetChalengeDetailsFailure> {
    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: Int): Either<Failure.GetChalengeDetailsFailure, ChalengeDetailles> {


        delay(4000)
        val res = ArrayList<Pair<String,String>>()
        res.add(Pair("resource 1 ","https://www.soundczech.cz/temp/lorem-ipsum.pdf"))
        res.add(Pair("resource 2 ","https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"))
        res.add(Pair("resource 3 ","http://www.bavtailor.com/wp-content/uploads/2018/10/Lorem-Ipsum.pdf"))
        res.add(Pair("resource 4 ","http://www.buds.com.ua/images/Lorem_ipsum.pdf"))

        val questions  =ArrayList<Question>()
        questions.add(Question("bla bla bla 1 ?",ArrayList()))
        questions.add(Question("bla bla bla 2 ?",ArrayList()))
        questions.add(Question("bla bla bla 3 ?",ArrayList()))
        questions.add(Question("bla bla bla 4 ?",ArrayList()))
        val detailles = ChalengeDetailles(0,100,res,questions)

        return Either.Right(detailles)
//      return repo.getChallengeDetaille(executeParams)
    }
}
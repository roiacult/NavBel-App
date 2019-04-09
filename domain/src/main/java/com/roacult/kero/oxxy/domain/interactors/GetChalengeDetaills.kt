package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.ChalengeDetailles
import com.roacult.kero.oxxy.domain.modules.Option
import com.roacult.kero.oxxy.domain.modules.Question
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetChalengeDetaills @Inject constructor(dispatchers: CouroutineDispatchers, val repo:MainRepository) : EitherInteractor<Int,ChalengeDetailles
        ,Failure.GetChalengeDetailsFailure> {
    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: Int): Either<Failure.GetChalengeDetailsFailure, ChalengeDetailles> {

//
        delay(3000)
        val res = ArrayList<Pair<String,String>>()
        res.add(Pair("resource 1 ","https://www.soundczech.cz/temp/lorem-ipsum.pdf"))
        res.add(Pair("resource 2 ","https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"))
        res.add(Pair("resource 3 ","http://www.bavtailor.com/wp-content/uploads/2018/10/Lorem-Ipsum.pdf"))
        res.add(Pair("resource 4 ","http://www.buds.com.ua/images/Lorem_ipsum.pdf"))

        val questions  =ArrayList<Question>()
        questions.add(Question(0,"bla bla bla 1 ?", arrayListOf(Option(0,"option1"),Option(1,"option2"),Option(2,"option3"))))
        questions.add(Question(1,"bla bla bla 2 ?",arrayListOf(Option(3,"option1"),Option(4,"option2"),Option(5,"option3"))))
        questions.add(Question(2,"bla bla bla 3 ?",arrayListOf(Option(6,"option1"),Option(7,"option2"),Option(8,"option3"))))
        questions.add(Question(3,"bla bla bla 4 ?",arrayListOf(Option(9,"option1"),Option(10,"option2"),Option(11,"option3"))))
        questions.add(Question(4,"bla bla bla 5 ?",arrayListOf(Option(12,"option1"),Option(13,"option2"),Option(14,"option3"))))
        val detailles = ChalengeDetailles(0,120,res,questions)

        return Either.Right(detailles)
//      return repo.getChallengeDetaille(executeParams)
    }
}
package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import kotlinx.coroutines.delay
import javax.inject.Inject
data class Answer(val optionId:Long , val time:Long )
class SubmitAnswer @Inject constructor(dispatchers: CouroutineDispatchers ,
                                       val repo:MainRepository) : EitherInteractor<SubmitionParam,SubmitionResult, Failure.SubmitionFailure> {
    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: SubmitionParam): Either<Failure.SubmitionFailure, SubmitionResult> {
       return repo.checkSubmit(executeParams)
    }
}
data class SubmitionParam(val answers : Map<Long,Answer>,  val chalengeId : Int)
data class SubmitionResult(val success : Boolean, val points : Long)
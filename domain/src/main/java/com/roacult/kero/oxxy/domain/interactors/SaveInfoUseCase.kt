package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import javax.inject.Inject

class SaveInfoUseCase @Inject constructor(dispatchers: CouroutineDispatchers) : EitherInteractor<UserInfo,None,Failure.SaveInfoFaillure>{

    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: UserInfo): Either<Failure.SaveInfoFaillure, None> {
        //for testing
        delay(5000)
        return Either.Right(None())
    }
}

data class UserInfo(val fName : String , val lName : String,val year : Int,val pass : String)
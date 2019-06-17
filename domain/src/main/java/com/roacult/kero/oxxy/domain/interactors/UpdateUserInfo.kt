package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import kotlinx.coroutines.delay
import javax.inject.Inject

class UpdateUserInfo @Inject constructor( private val repository: MainRepository , couroutineDispatchers: CouroutineDispatchers) : EitherInteractor<UpdateUserInfoParam,None, Failure.UpDateUserInfo> {

    override val dispatcher =couroutineDispatchers.computaion
    override val ResultDispatcher= couroutineDispatchers.main

    override suspend fun invoke(executeParams: UpdateUserInfoParam): Either<Failure.UpDateUserInfo, None> {
//        delay(3000)
//        return Either.Right(None())
        return repository.updateUserInfo(executeParams)
    }
}

data class UpdateUserInfoParam(val fname :String,val lName :String,val picture : String?,val public :Boolean , val password:String?)
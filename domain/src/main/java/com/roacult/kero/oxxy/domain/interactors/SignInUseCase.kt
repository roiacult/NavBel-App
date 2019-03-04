package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.AuthentificationRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import kotlinx.coroutines.delay
import javax.inject.Inject

class SignInUseCase @Inject constructor( dispatchers: CouroutineDispatchers, val repo:AuthentificationRepository) :
    EitherInteractor<String,MailResult,Failure.SignInFaillure> {

    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: String): Either<Failure.SignInFaillure, MailResult> {
        return repo.checkMail(executeParams)
//        delay(4000)
//        return Either.Right(MailResult(2,"djawed","benahamed"))

    }
}

data class MailResult(val year :Int  , val nom:String , val prenom :String )
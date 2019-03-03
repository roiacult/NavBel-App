package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.AuthentificationRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import javax.inject.Inject

class SignInUseCase @Inject constructor( dispatchers: CouroutineDispatchers, val repo:AuthentificationRepository) :
    EitherInteractor<String,MailResult,Failure.SignInFaillure> {

    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: String): Either<Failure.SignInFaillure, MailResult> {
        return repo.checkMail(executeParams)
    }
}

data class MailResult(val year :String , val nom:String , val prenom :String )
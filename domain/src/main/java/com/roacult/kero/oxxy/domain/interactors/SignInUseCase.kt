package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.AuthentificationRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import javax.inject.Inject

class SignInUseCase @Inject constructor( dispatchers: CouroutineDispatchers, val repo:AuthentificationRepository) :
    EitherInteractor<Mail,MailResult,Failure.SignInFailure> {

    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: Mail): Either<Failure.SignInFailure, MailResult> {
        return repo.checkMail(executeParams)
    }
}
data class Mail(val email:String)
data class MailResult(val year :String , val nom:String , val prenom :String )
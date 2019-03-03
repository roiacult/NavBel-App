package com.roacult.kero.oxxy.projet2eme.repositories

import com.roacult.kero.oxxy.domain.AuthentificationRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.MailResult
import javax.inject.Inject

class AutherntificationRepositoryImpl
    @Inject constructor():AuthentificationRepository {
    override fun checkMail(email: String): Either<Failure.SignInFailure, MailResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
package com.roacult.kero.oxxy.projet2eme.repositories

import com.roacult.kero.oxxy.domain.AuthentificationRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.MailResult
import com.roacult.kero.oxxy.projet2eme.network.AuthertificationRemote
import javax.inject.Inject

class AutherntificationRepositoryImpl
    @Inject constructor(val remote :AuthertificationRemote):AuthentificationRepository {
    override suspend fun checkMail(email: String): Either<Failure.SignInFaillure, MailResult> {
        return remote.CheckMailUser(email)
    }
}
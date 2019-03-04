package com.roacult.kero.oxxy.projet2eme.repositories

import com.roacult.kero.oxxy.domain.AuthentificationRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.MailResult
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.UserInfo
import com.roacult.kero.oxxy.projet2eme.local.AuthentificationLocal
import com.roacult.kero.oxxy.projet2eme.network.AuthertificationRemote
import com.roacult.kero.oxxy.projet2eme.network.entities.SaveInfoResult
import javax.inject.Inject

class AutherntificationRepositoryImpl
    @Inject constructor(val remote :AuthertificationRemote , val local :AuthentificationLocal):AuthentificationRepository {
    override suspend fun checkMail(email: String): Either<Failure.SignInFaillure, MailResult> {
        return remote.CheckMailUser(email)
    }

    override suspend fun saveUserInfo(user: UserInfo): Either<Failure.SaveInfoFaillure, None> {
        var either= remote.saveUserInfo(user)
        if (either.isRight) {
            val e = either as  Either.Right<SaveInfoResult>
            local.saveUSerLocal(e.b)
        }
        return either
    }
}
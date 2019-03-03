package com.roacult.kero.oxxy.domain

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.Mail
import com.roacult.kero.oxxy.domain.interactors.MailResult

interface AuthentificationRepository {
  fun checkMail(email: Mail):Either<Failure.SignInFailure , MailResult>
}
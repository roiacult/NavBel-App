package com.roacult.kero.oxxy.domain

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.LoginParam
import com.roacult.kero.oxxy.domain.interactors.MailResult
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.interactors.UserInfo

interface AuthentificationRepository {
 suspend  fun checkMail(email: String):Either<Failure.SignInFaillure , MailResult>
 suspend fun saveUserInfo(user:UserInfo):Either<Failure.SaveInfoFaillure , None>
 fun checkCodeCorrect(code:String):Either<Failure.ConfirmEmailFaillure , None>
 suspend fun logUserIn(loginParam: LoginParam):Either<Failure.LoginFaillure , None>
 suspend fun resendConfirmationCode(email: String):Either<Failure.ResendConfirmationFailure , None>
}
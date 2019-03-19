package com.roacult.kero.oxxy.domain

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.interactors.*
interface AuthentificationRepository {
 suspend  fun checkMail(email: String):Either<Failure.SignInFaillure , MailResult>
 suspend fun saveUserInfo(user:UserInfo):Either<Failure.SaveInfoFaillure , None>
 fun checkCodeCorrect(code:String):Either<Failure.ConfirmEmailFaillure , None>
 suspend fun logUserIn(loginParam: LoginParam):Either<Failure.LoginFaillure , None>
 suspend fun resendConfirmationCode(email: String):Either<Failure.ResendConfirmationFailure , None>
 suspend fun resetPassword(param:ResetPasswordParams):Either<Failure.ResetPasswordFailure, None>
 suspend  fun sendCodeResetPass(param:String):Either<Failure.SendCodeResetPassword , None>
 suspend  fun userState():Boolean
}
package com.roacult.kero.oxxy.domain.exception


sealed class Failure(val error : Throwable) {

    sealed class SignInFaillure(throwable: Throwable) : Failure(throwable){
        class UserNotFoundFaillurre( faille: Throwable) : SignInFaillure(faille)
        class AutherFaillure( faille: Throwable) : SignInFaillure(faille)
        class UserAlreadyExist( e:Throwable):SignInFaillure(e)
    }

    sealed class LoginFaillure(throwable: Throwable) : Failure(throwable){
        class UserNotSubscribedYet( faille: Throwable) : LoginFaillure(faille)
        class AutherFaillure( faille: Throwable) : LoginFaillure(faille)
    }
}

package com.roacult.kero.oxxy.domain.exception


sealed class Failure {

    sealed class SignInFaillure(val e:Exception?) : Failure(){
        class UserNotFoundFaillurre(e:Exception?) : SignInFaillure(e)
        class AutherFaillure(e:Exception?) : SignInFaillure(e)
        class UserAlreadyExist( e:Exception?):SignInFaillure(e)
        class UserBanned(e: Exception?):SignInFaillure(e)
    }

    sealed class LoginFaillure(throwable: Throwable) : Failure(){
        class UserNotSubscribedYet( faille: Throwable) : LoginFaillure(faille)
        class AutherFaillure( faille: Throwable) : LoginFaillure(faille)
    }
}

package com.roacult.kero.oxxy.domain.exception


sealed class Failure {

    sealed class SignInFaillure : Failure(){
        class UserNotFoundFaillurre : SignInFaillure()
        class AutherFaillure(val e:Throwable?) : SignInFaillure()
        class UserAlreadyExist:SignInFaillure()
        class UserBanned:SignInFaillure()
    }

    sealed class LoginFaillure(throwable: Throwable) : Failure(){
        class UserNotSubscribedYet( faille: Throwable) : LoginFaillure(faille)
        class AutherFaillure( faille: Throwable) : LoginFaillure(faille)
    }

    sealed class SaveInfoFaillure(val e : Exception?) : Failure(){

    }
}

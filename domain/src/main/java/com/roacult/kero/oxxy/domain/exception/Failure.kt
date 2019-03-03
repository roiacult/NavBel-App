package com.roacult.kero.oxxy.domain.exception


sealed class Failure {
  sealed class SignInFailure: Failure(){
      class UserDontExist(val e:Exception?):SignInFailure()
      class UserAlreadyExist(val e:Exception?):SignInFailure()
      class UserBAnned(val e:Exception?):SignInFailure()
      class ServerError(val e:Exception?):SignInFailure()
      class NetworkFailure(val e :Exception?):SignInFailure()
  }
}

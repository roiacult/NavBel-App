package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.AuthentificationRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import kotlinx.coroutines.delay
import javax.inject.Inject


    /**
     * return boolean (loged in) or (not loged in)
     * */
class UserStateUseCase @Inject constructor(dispatchers : CouroutineDispatchers,
    val repo:AuthentificationRepository):
        Interactor<None , Boolean> {

    override val dispatcher =dispatchers.computaion
    override val ResultDispatcher= dispatchers.main

    override suspend fun invoke(executeParams: None): Boolean {
//        return repo.userState()
        return true
    }
}

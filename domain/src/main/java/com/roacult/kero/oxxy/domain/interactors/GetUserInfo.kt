package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.User
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class GetUserInfo @Inject constructor( private val repository: MainRepository , couroutineDispatchers: CouroutineDispatchers) : Interactor<None,User> {
    override val dispatcher =couroutineDispatchers.computaion
    override val ResultDispatcher= couroutineDispatchers.main

    override suspend fun invoke(executeParams: None):User {
        return repository.getUserInfo()
//        val list = ArrayList<Int>()
//        for (i in 0..15 ) list.add(Random.nextInt(0,50))
//
//        delay(3000)
//        return User(1,"bla@email.com","djawed","benahmed",true,null,1,"2014",6,1200,14,list,"User description bbla bla")
    }
}
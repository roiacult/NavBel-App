package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.Post
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * get limited posts from remote
 * max numbre  60 post
 * */
class GetAllPosts @Inject constructor(
    couroutineDispatchers: CouroutineDispatchers,
    val repo: MainRepository
) :EitherInteractor<None , List<Post> , Failure.PostsFailure> {

    override val dispatcher =couroutineDispatchers.computaion
    override val ResultDispatcher= couroutineDispatchers.main

    override suspend fun invoke(executeParams: None): Either<Failure.PostsFailure, List<Post>> {
        delay(3000)

        val list = ArrayList<Post>()
        for(i in 0..10 ){
            list.add(
                Post(i.toLong(),
                    "https://1.bp.blogspot.com/-URnpXP3eqH0/XHUvQR1waSI/AAAAAAAADXA/v_yIGMhbS9MqcF7I6ju-uFFLePrf8TIMgCK4BGAYYCw/s1600/3196.jpg",
                    "Bureautique et web, -Chapitre 2 MICROSOFT WORD 2007 - Première Année ...\n" +
                            "Première Année ST SM USTHB\n" +
                            "-Bureautique et web, -Chapitre 2 MICROSOFT WORD 2007" ,
                            i.toLong() ,"Djawwd benahmed" , "2 Cpi" , "")
            )

        }

        return Either.Right(list)
    }
}
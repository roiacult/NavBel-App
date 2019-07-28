package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.Comment
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * get post comments
 * i will pass post id (Long)
 * */
class PostDetailes  @Inject constructor(
    couroutineDispatchers: CouroutineDispatchers,
    val repo: MainRepository
) :EitherInteractor<Long , List<Comment> , Failure.PostsFailure> {

    override val dispatcher =couroutineDispatchers.computaion
    override val ResultDispatcher= couroutineDispatchers.main

    override suspend fun invoke(executeParams: Long): Either<Failure.PostsFailure, List<Comment>> {
        // todo
        delay(3000)
        val list = ArrayList<Comment>()

        for( i in 0.. 10 ) {
            list.add(
                Comment(i.toLong(),"","Djawed Benahmed",2,"bla bla bla  blla blaaaa jhbiud jiudcs")
            )
        }

        return Either.Right(list)
    }
}
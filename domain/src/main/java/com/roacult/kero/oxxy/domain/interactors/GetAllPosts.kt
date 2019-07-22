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

        return Either.Right(arrayListOf())
    }
}
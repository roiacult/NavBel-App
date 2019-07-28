package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.Post
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * and store it in post table
 * */
class Posting  @Inject constructor(
    couroutineDispatchers: CouroutineDispatchers,
    val repo: MainRepository
) :EitherInteractor<Post , None , Failure.PostsFailure> {

    override val dispatcher =couroutineDispatchers.computaion
    override val ResultDispatcher= couroutineDispatchers.main

    override suspend fun invoke(executeParams: Post): Either<Failure.PostsFailure, None> {
        return repo.createPost(executeParams)
//        delay(3000)
//        return Either.Right(None())
    }
}
package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * post a comment to server
 * */
class CommentPost  @Inject constructor(
    couroutineDispatchers: CouroutineDispatchers,
    val repo: MainRepository
) :EitherInteractor< String ,None , Failure.PostsFailure> {

    override val dispatcher =couroutineDispatchers.computaion
    override val ResultDispatcher= couroutineDispatchers.main

    override suspend fun invoke(executeParams: String): Either<Failure.PostsFailure, None> {
        // todo
        delay(300)
        return Either.Right(None())
    }
}
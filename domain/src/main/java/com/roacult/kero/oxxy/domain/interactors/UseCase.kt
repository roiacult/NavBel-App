/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.roacult.kero.oxxy.domain.interactors

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.AppRxSchedulers
import com.roacult.kero.oxxy.domain.functional.Either

interface EitherInteractor<in P, out R> {
    val dispatcher: CoroutineDispatcher
    suspend operator fun invoke(executeParams: P): Either<Failure, R>
}

interface Interactor<in P>{
    val  dispatcher: CoroutineDispatcher
    suspend operator fun invoke(executeParams: P)
}

abstract class ObservableInteractor<Type , in Params>(private val schedulers: AppRxSchedulers){

    protected abstract fun buildObservable(p:Params):Observable< Type>

    fun observe(p:Params, observer: (e: Either<Failure, Type>)->Unit): Disposable {
       return buildObservable(p).subscribeOn(schedulers.io).observeOn(schedulers.main)
            .subscribe({
                observer(Either.Right(it))
            }, {
                observer(Either.Left(Failure.DataBaseError(it)))
            })
    }
}

fun <P, R> CoroutineScope.launchInteractor(interactor: EitherInteractor<P, R>, param: P, OnResult:(Either<Failure, R>)->Unit): Job {
   val  job = async { interactor(param) }
    return launch(interactor.dispatcher) { OnResult(job.await()) }
}
class None


fun <P> CoroutineScope.launchInteractor(interactor: Interactor<P>, param: P, onResult:()->Unit):Job{
    return launch (interactor.dispatcher){ interactor(param)
                        onResult()}

}
fun  <R>CoroutineScope.launchInteractor(interactor: EitherInteractor<Unit, R>, OnResult: (Either<Failure, R>) -> Unit) =
    launchInteractor(interactor, Unit, OnResult = OnResult)

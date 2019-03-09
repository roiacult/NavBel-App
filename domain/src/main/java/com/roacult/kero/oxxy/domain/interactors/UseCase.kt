package com.roacult.kero.oxxy.domain.interactors

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.AppRxSchedulers
import com.roacult.kero.oxxy.domain.functional.Either

interface EitherInteractor<in P, out R , out F :Failure> {
    val dispatcher: CoroutineDispatcher
    val ResultDispatcher :CoroutineDispatcher
    suspend operator fun invoke(executeParams: P):Either<F, R>
}

interface Interactor<in P , out R >{
    val  dispatcher: CoroutineDispatcher
    val ResultDispatcher :CoroutineDispatcher
    suspend operator fun invoke(executeParams: P):R
}

abstract class ObservableInteractor<Type , in Params>(private val schedulers:AppRxSchedulers){

    protected abstract fun buildObservable(p:Params):Observable< Type>

    fun observe(p:Params, FailureObserver:(e:Throwable)->Unit , SuccesObserver:(t:Type)->Unit): Disposable {
        return buildObservable(p).subscribeOn(schedulers.io).observeOn(schedulers.main)
            .subscribe(SuccesObserver, FailureObserver)
    }
}
abstract class ObservableCompleteInteractor<Type , in Params>(private val schedulers:AppRxSchedulers){

    protected abstract fun buildObservable(p:Params):Observable< Type>

    fun observe(p:Params, FailureObserver:(e:Throwable)->Unit , SuccesObserver:(t:Type)->Unit, JobCompletedObserver
    :()->Unit): Disposable {
        return buildObservable(p).subscribeOn(schedulers.io).observeOn(schedulers.main)
            .subscribe(SuccesObserver, FailureObserver, JobCompletedObserver)
    }
}

fun <P, R, T:Failure> CoroutineScope.launchInteractor(interactor: EitherInteractor<P,R , T>, param: P , OnResult:(Either<T, R>)->Unit): Job {
    val  job = async(interactor.dispatcher) { interactor(param) }
    return launch(interactor.ResultDispatcher) { OnResult(job.await()) }
}
class None


fun <P , R> CoroutineScope.launchInteractor(interactor:Interactor<P , R>, param: P, onResult:(R)->Unit):Job{
    val job = async(context = interactor.dispatcher){interactor(param)}
    return launch (interactor.ResultDispatcher){
        onResult(job.await())}

}

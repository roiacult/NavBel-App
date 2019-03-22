package  com.roacult.kero.oxxy.projet2eme.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.interactors.ObservableCompleteInteractor
import com.roacult.kero.oxxy.domain.interactors.ObservableInteractor
import com.roacult.kero.oxxy.domain.interactors.SubjectInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job


abstract  class BaseViewModel<S:State>(initialState:S): androidx.lifecycle.ViewModel() {
    protected val state:MutableLiveData<S> by lazy {
       var liveData:MutableLiveData<S> = MutableLiveData()
        liveData.value=initialState
        liveData
    }
    private val job = Job()
    protected val scope  = CoroutineScope(Dispatchers.Main+job)
    protected  val disposable:CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        if(job.isActive){
            job.cancel()
        }
        disposable.dispose()
    }
    protected fun setState( statechenger :S.()->S){
        state.value = state.value?.statechenger()
    }

    fun observe(lifecycleOwner: LifecycleOwner, observer : ((s:S)->Unit))
    {
        state.observe(lifecycleOwner, Observer(observer))
    }
    fun withState(chenger :(s:S)->Unit){
        chenger(state.value!!)
    }

    protected fun <P,Type> launchSubjectInteractor(interactor : SubjectInteractor<Type,P>,
                                                   param :P,
                                                   onNext : (Type)->Unit,
                                                   onError : (Throwable)->Unit,
                                                   onComplete: () -> Unit) :Disposable{

        val dis = interactor.observe(param,onNext,onError,onComplete)
        disposable.add(dis)
        // i return this in case i wanted to dispose only this one
        //and keep auther disposable inside compositeDisposable
        return dis
    }

    protected fun  <P, Type> launchObservableInteractor(interactor: ObservableInteractor<Type, P>, p:P, errorHandler :(Throwable)->Unit
                                                        , dataHandler:(Type)->Unit){
        disposable.add(interactor.observe(p, errorHandler, dataHandler))
    }
    protected fun  <P, Type> launchObservableCompletedInteractor(interactor: ObservableCompleteInteractor<Type, P>, p:P, errorHandler :(Throwable)->Unit
                                                                 , dataHandler:(Type)->Unit, onComplete:()->Unit){
        disposable.add(interactor.observe(p, errorHandler, dataHandler, onComplete))
    }

}


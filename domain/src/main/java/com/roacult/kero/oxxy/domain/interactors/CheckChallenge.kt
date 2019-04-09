package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.functional.AppRxSchedulers
import io.reactivex.Observable
import javax.inject.Inject

class CheckChallenge @Inject constructor(private val mainRepository: MainRepository, schedulers: AppRxSchedulers)
    :ObservableCompleteInteractor<Int , Int >(schedulers) {
    override fun buildObservable(p: Int): Observable<Int> {
        return mainRepository.checkChallenge(p)
    }
    fun clear(){
        mainRepository.clearObservable()
    }

}
package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.functional.AppRxSchedulers
import io.reactivex.Observable

class CheckChallenge(val mainRepository: MainRepository , schedulers: AppRxSchedulers) :SubjectInteractor<Boolean , Int >(schedulers) {
    override fun buildObservable(p: Int): Observable<Boolean> {
        return mainRepository.checkChallenge()
    }
}
data class  CheckChallengeParam(val id:Int , val challengeTime:Int)
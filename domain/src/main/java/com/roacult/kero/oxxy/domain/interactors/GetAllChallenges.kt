package com.roacult.kero.oxxy.domain.interactors

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.CouroutineDispatchers
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetAllChallenges @Inject constructor(couroutineDispatchers: CouroutineDispatchers)
    :EitherInteractor<None , List<ChalengeGlobale> ,Failure.GetAllChalengesFailure> {


    override val dispatcher =couroutineDispatchers.computaion
    override val ResultDispatcher= couroutineDispatchers.main

    override suspend fun invoke(executeParams: None): Either<Failure.GetAllChalengesFailure, List<ChalengeGlobale>> {
        delay(3000)
        val tmp = ArrayList<ChalengeGlobale>()
        //TODO  for testing
        var sch = ChalengeGlobale(1,"module1","numbre of resums recived  2M *** 380k" +
                "numbre of employers  88k *** 10k" +
                "number of resumes accepted 1.7% (34k) *** 6% (22k)" +
                "numbre of candidat passt interviews and get the job 0.1%,076%" +
                "number of candidat hired   2000 *** 470",120,3,7)
        tmp.add(sch)
        sch = ChalengeGlobale(2,"module2","numbre of resums recived  2M *** 380k" +
                "numbre of employers  88k *** 10k" +
                "number of resumes accepted 1.7% (34k) *** 6% (22k)" +
                "numbre of candidat passt interviews and get the job 0.1%,076%" +
                "number of candidat hired   2000 *** 470",150,3,7)
        tmp.add(sch)
        sch = ChalengeGlobale(3,"module3","numbre of resums recived  2M *** 380k" +
                "numbre of employers  88k *** 10k" +
                "number of resumes accepted 1.7% (34k) *** 6% (22k)" +
                "numbre of candidat passt interviews and get the job 0.1%,076%" +
                "number of candidat hired   2000 *** 470",200,3,7)
        tmp.add(sch)
        return  Either.Right(tmp)
    }
}
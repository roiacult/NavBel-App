package com.roacult.kero.oxxy.domain

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale

interface MainRepository {
    suspend  fun getAllChallenges():Either<List<ChalengeGlobale> , Failure.GetAllChalengesFailure>
}
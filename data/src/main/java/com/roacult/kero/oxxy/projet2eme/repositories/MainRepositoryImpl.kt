package com.roacult.kero.oxxy.projet2eme.repositories

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import com.roacult.kero.oxxy.projet2eme.network.MainRemote
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(val remote :MainRemote):MainRepository {
    override suspend fun getAllChallenges(): Either<List<ChalengeGlobale>, Failure.GetAllChalengesFailure> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
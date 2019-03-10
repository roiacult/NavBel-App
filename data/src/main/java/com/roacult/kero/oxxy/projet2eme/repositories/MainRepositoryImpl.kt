package com.roacult.kero.oxxy.projet2eme.repositories

import com.roacult.kero.oxxy.domain.MainRepository
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import com.roacult.kero.oxxy.projet2eme.local.MainLocal
import com.roacult.kero.oxxy.projet2eme.network.MainRemote
import javax.inject.Inject

/**
 * this is our main repository he will handle all business logic in the main feature local and remote
 * he implement the interface defined in the domaain layer
 */
class MainRepositoryImpl @Inject constructor( private val remote :MainRemote  ,private val local :MainLocal):MainRepository {
    override suspend fun getAllChallenges(): Either< Failure.GetAllChalengesFailure  , List<ChalengeGlobale>> {
    return remote.getChallenges(local.getYear())
    }
}
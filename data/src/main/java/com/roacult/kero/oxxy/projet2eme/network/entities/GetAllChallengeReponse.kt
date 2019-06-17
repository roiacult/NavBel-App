package com.roacult.kero.oxxy.projet2eme.network.entities

import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale

data class GetAllChallengeReponse(val reponse:Int, val challenges: List<ChalengeGlobale>?)
data class Request( val year :Int , val id:Int)
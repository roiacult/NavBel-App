package com.roacult.kero.oxxy.projet2eme.network.entities

import com.roacult.kero.oxxy.domain.modules.Question

data class ChallengeId(val id:Int   )
data class ChallengeDetailleReponse(val reponse:Int , val id : Int?,val time : Int? ,val resources : List<Ressource>?, val questions : List<Question>?)
data class Ressource(val url :String ,val name :String )
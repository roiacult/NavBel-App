package com.roacult.kero.oxxy.projet2eme.network.entities

import com.roacult.kero.oxxy.domain.modules.Question

data class ChallengeId(val id:Int   )
data class ChallengeDetailleReponse(val reponse:Int , val id : Int?,val time : Int? ,val resource : List<Ressource>?, val questions : List<QuestionReponse>?)
data class Ressource(val url :String ,val name :String )
//TODO change it and add question isFinished
data class QuestionReponse(val id:Int , val question:String , val options:List<OptionRepone>)
data class OptionRepone(val id:Int , val trueoption:String )
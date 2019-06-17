package com.roacult.kero.oxxy.projet2eme.network.entities


data class ChallengeId(val id:Int   )
data class ChallengeDetailleReponse(val reponse:Int , val id : Int? ,val resource : List<Ressource>?, val questions : List<QuestionReponse>?)
data class Ressource(val url :String ,val name :String )
data class QuestionReponse(val id:Int , val question:String , val options:List<OptionRepone>, val time :Long )
data class OptionRepone(val id:Int , val trueoption:String )
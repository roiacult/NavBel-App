package com.roacult.kero.oxxy.projet2eme.network.entities


data class UserAnswers(val challengeid:Long  , val id:Int , val challenges: List<RemoteQuestionAnswer>)
data class RemoteQuestionAnswer(val questionid:Long, val optionid:Long, val time:Boolean )
data class CorrectionResult(val reponse:Int , val totalwin:Long )

package com.roacult.kero.oxxy.projet2eme.network.entities

data class UserAnswers(val challengeid:Long  , val id:Long , val time:Long , val challenges: List<QuestionAnswer>)
data class QuestionAnswer(val questionid:Long , val optionid:Long)
data class CorrectionResult(val reponse:Int , val playerPoint:Long )

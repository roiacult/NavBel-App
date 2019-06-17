package com.roacult.kero.oxxy.projet2eme.network.entities

import com.roacult.kero.oxxy.domain.interactors.Answer

data class UserAnswers(val challengeid:Long  , val id:Int , val challenges: List<QuestionAnswer>)
data class QuestionAnswer(val questionid:Long , val answer:Answer)
data class CorrectionResult(val reponse:Int , val playerPoint:Long )

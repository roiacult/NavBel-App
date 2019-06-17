package com.roacult.kero.oxxy.projet2eme.network.entities

data class SetUserTry(val id:Int ,val challengeid:Int )
data class  SetUserResponse(val reponse:Int ,val  answers:List<AnswerResponse>)
data class AnswerResponse(val questionId:Long , val optionId:Long)

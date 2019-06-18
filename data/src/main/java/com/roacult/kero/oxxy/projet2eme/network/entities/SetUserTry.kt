package com.roacult.kero.oxxy.projet2eme.network.entities

data class SetUserTry(val id:Int ,val challengeid:Int )
data class  SetUserResponse(val reponse:Int ,val  data:List<AnswerResponse>)
data class AnswerResponse(val id:Long , val opt:Long)
fun List<AnswerResponse>.mapRemoteAnswersToDomain():Map<Long , Long>{
    val map = mutableMapOf<Long , Long >()
    forEach {
        map += it.id  to it.opt
    }
    return map
}
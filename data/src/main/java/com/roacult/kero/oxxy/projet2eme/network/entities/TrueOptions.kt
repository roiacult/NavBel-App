package com.roacult.kero.oxxy.projet2eme.network.entities

data class TrueOptions(val repoonse:Int, val options: List<TrueOption >? , val time :Long )
data class TrueOptionParam(val challengeId: Int )

data class TrueOption(val questionId:Long ,val optionId:Long , val point:Long)
data class  AddPointParam(val userId:Long , val point :Long )

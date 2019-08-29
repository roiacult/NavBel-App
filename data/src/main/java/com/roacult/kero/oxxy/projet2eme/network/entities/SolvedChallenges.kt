package com.roacult.kero.oxxy.projet2eme.network.entities

data class SolvedChallengeResult(val id:Int , val challengeid:Int , val challengepts:Int , val resultpts:Int, val mdl:String ,val image:String)
data class SolvedChallengesResult(val reponse:Int ,val data:List<SolvedChallengeResult>)
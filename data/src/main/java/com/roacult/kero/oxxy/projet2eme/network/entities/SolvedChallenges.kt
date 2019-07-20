package com.roacult.kero.oxxy.projet2eme.network.entities

data class SolvedChallengeResult(val id:Int , val challengeid:Int , val challengepts:Int , val resultpts:Int, val module:String ,val imageUrl:String)
data class SolvedChallengesResult(val response:Int ,val data:List<SolvedChallengeResult>)
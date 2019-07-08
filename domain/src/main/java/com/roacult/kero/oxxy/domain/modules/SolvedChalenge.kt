package com.roacult.kero.oxxy.domain.modules

data class SolvedChalenge (
    val id : Long,
    val imageUrl : String,
    val resultPoints : Int,
    val timePercent : Float,
    val pointPercent : Float,
    val moduleName :String
)
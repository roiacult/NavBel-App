package com.roacult.kero.oxxy.domain.modules

data class SolvedChalenge (
    val id : Long ,
    val imageUrl : String,
    val point : Int,
    val result : Int ,
    val moduleName :String
)
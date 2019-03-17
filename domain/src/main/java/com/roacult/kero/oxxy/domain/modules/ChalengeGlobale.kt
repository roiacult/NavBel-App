package com.roacult.kero.oxxy.domain.modules

data class ChalengeGlobale(
    val id  :Int,
    val module : String,
    val story : String,
    val url : String,
    val point : Int,
    val nbPersonSolved : Int,
    val nbOfQuestions : Int
)
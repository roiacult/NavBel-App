package com.roacult.kero.oxxy.domain.modules

data class ChalengeGlobale(
    val id  :Int ,
    val module : String ,
    val story : String,
    val image : String ,
    val point : Int ,
    val nbPersonSolveded : Int,
    val nbOfQuestions : Int
)
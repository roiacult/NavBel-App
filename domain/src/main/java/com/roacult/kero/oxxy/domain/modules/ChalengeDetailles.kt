package com.roacult.kero.oxxy.domain.modules


data class ChalengeDetailles(val id : Int,val time : Int ,val resources : List<Pair<String,String>>, val questions : List<Question>)
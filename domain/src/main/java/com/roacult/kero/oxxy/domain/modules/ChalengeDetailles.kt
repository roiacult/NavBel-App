package com.roacult.kero.oxxy.domain.modules

/**
 * time -> time neded to solve this chalenge(on seconds)
 * resources -> list of url (pdf)
 * you can change the id to String if you want but don't
 * forget to change it in ChalngeGlobale to
 * */
data class ChalengeDetailles(val id : Int,val time : Int ,val resources : List<String>, val questions : List<Question>)
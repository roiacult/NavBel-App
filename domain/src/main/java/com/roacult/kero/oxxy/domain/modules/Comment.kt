package com.roacult.kero.oxxy.domain.modules

data class Comment (
    val userId : Long ,
    val userImage : String ,
    val userName : String ,
    val userYear : Int ,
    val commentContent : String
)
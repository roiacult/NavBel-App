package com.roacult.kero.oxxy.domain.modules

data class Post (
    val postId : Long ,
    val postImage : String? ,
    val postDesc : String ,
    val userId : Long ,
    val userName : String ,
    val userYear : String ,
    val userImage : String
)
package com.roacult.kero.oxxy.projet2eme.network.entities

data class UpdateUserParam(val id:Int ,val  fname:String ,val lname:String , val picture:String , val ispublic:Boolean, val password:String?  )
data class UpdateUserResult(val reponse :Int, val picture: String)
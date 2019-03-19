package com.roacult.kero.oxxy.projet2eme.network.entities

data class Mail(val email:String)
data class MailResponse(val reponse: Int , val fname :String? , val lname:String? , val year:Int? )
data class Code( val reponse: String , val code:Int )
data class CofirmMail(val banne:Int , val why:String? , val email:String? , val fname: String?=null)
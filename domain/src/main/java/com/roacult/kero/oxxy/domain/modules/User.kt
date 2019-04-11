package com.roacult.kero.oxxy.domain.modules

data class User(val id :Int,
                val email :String ,
                val fname :String ,
                val lName :String ,
                val picture :String ,
                val year : Int ,
                val date : String)

//(val email: String? ,val fname:String? , val lname:String? ,  val picture:String? ,
//val year :Int? ,
//val date:String?,
//val id:Long?,
//val reponse:Int)
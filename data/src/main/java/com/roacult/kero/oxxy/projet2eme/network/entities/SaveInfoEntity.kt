package com.roacult.kero.oxxy.projet2eme.network.entities

data class SaveInfo(val email:String , val fname:String , val lname:String , val password:String
                    , val picture:String , val year:Int
                    )
data class SaveInfoResult(val email: String? ,val fname:String? , val lname:String? ,  val imageUrl:String? ,
                          val year :Int? ,
                          val date:String?,
                          val id:Long?,
                          val reponse:Int)
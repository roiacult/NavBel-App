package com.roacult.kero.oxxy.projet2eme.network.entities

data class SaveInfo(val email:String , val fname:String , val lname:String , val pass:String , val image:String , val year:Int
                    )
data class SaveInfoResult(val email: String? ,val fname:String? , val lname:String? ,  val imageUrl:String? , val year:Int?,
                          val reponse:Int)
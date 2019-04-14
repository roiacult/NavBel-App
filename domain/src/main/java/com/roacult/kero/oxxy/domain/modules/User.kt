package com.roacult.kero.oxxy.domain.modules

data class User(val id :Int,
                val email :String,
                val fname :String,
                val lName :String,
                val public : Boolean,
                val picture :String?,
                val year : Int,
                val date : String,
                val nbSolved : Int,
                val point :Int,
                val currentRank : Int,
                val ranks : ArrayList<Int>)
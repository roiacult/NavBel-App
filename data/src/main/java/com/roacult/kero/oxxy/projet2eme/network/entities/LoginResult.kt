package com.roacult.kero.oxxy.projet2eme.network.entities

data class LoginResult(val reponse:Int , val email :String , val fname:String , val lname:String
                       ,val imageUrl:String , val currentRank:Int , val nqsolved:Int   , val date:String ,
                       val point :Int
//                       ,val rankTable:Array<Int>
                       ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginResult

        if (reponse != other.reponse) return false
        if (email != other.email) return false
        if (fname != other.fname) return false
        if (lname != other.lname) return false
        if (imageUrl != other.imageUrl) return false
        if (currentRank != other.currentRank) return false
        if (nqsolved != other.nqsolved) return false
        if (date != other.date) return false
        if (point != other.point) return false
//        if (!rankTable.contentEquals(other.rankTable)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = reponse
        result = 31 * result + email.hashCode()
        result = 31 * result + fname.hashCode()
        result = 31 * result + lname.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + currentRank
        result = 31 * result + nqsolved
        result = 31 * result + date.hashCode()
        result = 31 * result + point
//        result = 31 * result + rankTable.contentHashCode()
        return result
    }
}
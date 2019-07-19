package com.roacult.kero.oxxy.projet2eme.network.entities

data class  LoginResult(val reponse:Int, val email :String?, val fname:String?, val lname:String?,
                            val year:Int?, val id:Int?
                            ,
                            val picture:String?, val currentrank:Int?
                            , val nbsolved:Int?, val date:String?,
                            val point :Int?
                           , val  ispublic:Int? ,
                        val bio:String?
                             , val ranks:Array<Int>?
                       ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoginResult

        if (reponse != other.reponse) return false
        if (email != other.email) return false
        if (fname != other.fname) return false
        if (lname != other.lname) return false
        if (year != other.year) return false
        if (id != other.id) return false
        if (picture != other.picture) return false
        if (currentrank != other.currentrank) return false
        if (nbsolved != other.nbsolved) return false
        if (date != other.date) return false
        if (point != other.point) return false
        if (ispublic != other.ispublic) return false
        if (bio != other.bio) return false
        if (ranks != null) {
            if (other.ranks == null) return false
            if (!ranks.contentEquals(other.ranks)) return false
        } else if (other.ranks != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = reponse
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (fname?.hashCode() ?: 0)
        result = 31 * result + (lname?.hashCode() ?: 0)
        result = 31 * result + (year ?: 0)
        result = 31 * result + (id ?: 0)
        result = 31 * result + (picture?.hashCode() ?: 0)
        result = 31 * result + (currentrank ?: 0)
        result = 31 * result + (nbsolved ?: 0)
        result = 31 * result + (date?.hashCode() ?: 0)
        result = 31 * result + (point ?: 0)
        result = 31 * result + (ispublic ?: 0)
        result = 31 * result + (bio?.hashCode() ?: 0)
        result = 31 * result + (ranks?.contentHashCode() ?: 0)
        return result
    }
}

data class LoginParame(val banne:Int , val why :String? , val email :String , val password:String )
data class Reponse(val reponse:Int)
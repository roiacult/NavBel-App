package com.roacult.kero.oxxy.projet2eme.network.entities

data class LoginResult(val reponse:Int , val email :String? , val fname:String? , val lname:String?,
                       val year:Int? , val id:Int?
                       ,
                       val picture:String? , val currentrank:Int?
                       , val nbsolved:Int?   , val date:String? ,
                       val point :Int?
                       ,val rankTable:Array<Int>?
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
        if (picture != other.picture) return false
        if (currentrank != other.currentrank) return false
        if (nbsolved != other.nbsolved) return false
        if (date != other.date) return false
        if (point != other.point) return false
        if (rankTable != null) {
            if (other.rankTable == null) return false
            if (!rankTable.contentEquals(other.rankTable)) return false
        } else if (other.rankTable != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = reponse
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (fname?.hashCode() ?: 0)
        result = 31 * result + (lname?.hashCode() ?: 0)
        result = 31 * result + (year ?: 0)
        result = 31 * result + (picture?.hashCode() ?: 0)
        result = 31 * result + (currentrank ?: 0)
        result = 31 * result + (nbsolved ?: 0)
        result = 31 * result + (date?.hashCode() ?: 0)
        result = 31 * result + (point ?: 0)
        result = 31 * result + (rankTable?.contentHashCode() ?: 0)
        return result
    }


}
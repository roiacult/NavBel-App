package com.roacult.kero.oxxy.projet2eme.utils

import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.ui.main.fragments.chalenge_fragment.Filter

fun getFilter(module : String , minPointStr : String , maxPointStr : String, minQuesStr : String , maxQuesStr : String) :Filter {

        val maxPoint =  if(maxPointStr.isEmpty()) -1 else maxPointStr.toInt()
        val minPoint =  if(minPointStr.isEmpty()) -1 else minPointStr.toInt()
        val maxQues =  if(maxQuesStr.isEmpty()) -1 else maxQuesStr.toInt()
        val minQues =  if(minQuesStr.isEmpty()) -1 else minQuesStr.toInt()

        return Filter(if(module.isEmpty() || module == "none") null else module ,
                if(maxPoint< 0) -1 else maxPoint ,
                if(minPoint<0 ) -1 else minPoint,
                if(maxQues< 0) -1 else maxQues ,
                if(minQues<0 ) -1 else minQues
        )
}
fun getId(position : Int ): Int =
        when(position){
                0 -> R.id.chalenge_page
                1 -> R.id.forum_page
                2 -> R.id.award_page
                else -> R.id.profile_page
        }
fun getPosition(id : Int): Int =
        when(id){
                R.id.chalenge_page -> 0
                R.id.forum_page -> 1
                R.id.award_page -> 2
                else -> 3
        }
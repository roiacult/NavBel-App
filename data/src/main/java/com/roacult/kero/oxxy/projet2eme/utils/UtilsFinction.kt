package com.roacult.kero.oxxy.projet2eme.utils

import com.roacult.kero.oxxy.domain.modules.Option
import com.roacult.kero.oxxy.domain.modules.Question
import com.roacult.kero.oxxy.projet2eme.network.entities.QuestionReponse
import com.roacult.kero.oxxy.projet2eme.network.entities.Ressource

fun List<Ressource>.fromRessourceToPair():List<Pair<String , String>> = this.map {
    Pair(it.name , it.url)
}
fun List<QuestionReponse>.mapToQuestion():List<Question>{
   return  this.map {
        Question(it.id, it.question , it.options.map {
            Option(it.id , it.trueoption)
        })
    }
}
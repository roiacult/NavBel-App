package com.roacult.kero.oxxy.projet2eme.utils

import com.roacult.kero.oxxy.projet2eme.network.entities.Ressource

fun List<Ressource>.fromRessourceToPair():List<Pair<String , String>> = this.map {
    Pair(it.name , it.url)
}
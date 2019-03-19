package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.chalenge_fragment

import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale

class Filter(private val  module  :String?,
             private val maxPoint  : Int,
             private val minPoint :Int,
             private val maxNbQuestion:Int ,
             private val minNbQuestion :Int){

    constructor() : this(null,-1,-1,-1,-1)

    fun filter(chalenges : List<ChalengeGlobale>) : List<ChalengeGlobale>{
        var list = chalenges.toMutableList()
        if(module != null){
            list = list.filter {md ->  md.module == module }.toMutableList()
        }
        if(maxPoint != -1 ){
            list = list.filter { md -> md.point<= maxPoint }.toMutableList()
        }
        if(minPoint != -1){
            list = list.filter { md -> md.point>= minPoint }.toMutableList()
        }
        if(maxNbQuestion != -1 ){
            list = list.filter { md -> md.nbOfQuestions<= maxNbQuestion }.toMutableList()
        }
        if(minNbQuestion != -1){
            list = list.filter { md -> md.nbOfQuestions>= minNbQuestion }.toMutableList()
        }
        return list
    }
}
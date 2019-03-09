package com.roacult.kero.oxxy.domain.modules

/**
 * this module i will requested in main fragment and i will show it
 * once user click on start button i will send you another request
 * with chalenge id to get all Chalenge detailles
 * i seprate them like this because it might chalenge alredy solved by 5 peaple when user
 * click on start button so we need to test if the chalnge is still availble or not
 * */
data class ChalengeGlobale(val id  :Int ,val module : String , val story : String , val point : Int ,val nbPersonSolveded : Int,val nbOfQuestions : Int)
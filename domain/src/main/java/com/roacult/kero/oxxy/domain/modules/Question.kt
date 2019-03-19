package com.roacult.kero.oxxy.domain.modules

/**
 * data module class for representig question
 * contain the question and his options
 * */
data class Question(val id :Int , val question : String , val options : List<Option>)
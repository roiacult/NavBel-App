package com.roacult.kero.oxxy.projet2eme.utils.extension

import androidx.recyclerview.widget.SortedList
import com.roacult.kero.oxxy.domain.interactors.QuestionAnswer
import com.roacult.kero.oxxy.domain.modules.Question
import java.util.regex.Matcher
import java.util.regex.Pattern

fun String.isEmailValid(): Boolean {
    val pattern: Pattern
    val matcher: Matcher
    val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    pattern = Pattern.compile(EMAIL_PATTERN)
    matcher = pattern.matcher(this)
    return matcher.matches()
}

fun <D> SortedList<D>.toList() : List<D>{
    val list = ArrayList<D>()
    for (i in 0 until this.size()){
        list.add(this[i])
    }
    return list
}

val MutableMap<Long,QuestionAnswer>.questionSolved : Int
    get() {
        var count = 0
        for (id in this){
            if(id.value.optionId != -1L) count++
        }
        return count
    }
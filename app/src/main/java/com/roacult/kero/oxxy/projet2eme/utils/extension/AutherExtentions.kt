package com.roacult.kero.oxxy.projet2eme.utils.extension

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
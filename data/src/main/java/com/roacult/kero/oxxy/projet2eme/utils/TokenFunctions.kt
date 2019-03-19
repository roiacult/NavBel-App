package com.roacult.kero.oxxy.projet2eme.utils

import com.roacult.kero.oxxy.data.BuildConfig
import java.security.MessageDigest
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

 fun toHexString(bytes: ByteArray): String {
    val formatter = Formatter()
    for (b in bytes) {
        formatter.format("%02x", b)
    }
    return formatter.toString()
}
fun token():String {
    val time = (Date().time/1000)/60
    var diggest = MessageDigest.getInstance("md5")
    diggest.update(time.toString().toByteArray())
    val md5OfTime = toHexString(diggest.digest())
    diggest = MessageDigest.getInstance("SHA-256")
    diggest.update("$time${BuildConfig.ApiKey}".toByteArray())
    val key = diggest.digest()
    val mac = Mac.getInstance("HmacSHA256")
    val secretKeySpec = SecretKeySpec(toHexString(key).toByteArray() , "HmacSHA256")
    mac.init(secretKeySpec)
    val secret = mac.doFinal(md5OfTime.toByteArray())
    return  toHexString(secret)
}
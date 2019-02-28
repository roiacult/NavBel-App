package com.roacult.kero.oxxy.projet2eme.utils

import android.os.Build
import java.io.IOException
import java.util.*

class UserAgentInterceptor(val userAgent: String) : Interceptor {

    constructor(appName: String, appVersion: String) : this(
        String.format(
            Locale.US,
            "%s/%s (Android %s; %s; %s %s; %s)",
            appName,
            appVersion,
            Build.VERSION.RELEASE,
            Build.MODEL,
            Build.BRAND,
            Build.DEVICE,
            Locale.getDefault().getLanguage()
        )
    )
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val userAgentRequest = chain.request()
            .newBuilder()
            .header("User-Agent", userAgent)
            .build()
        return chain.proceed(userAgentRequest)
    }
}
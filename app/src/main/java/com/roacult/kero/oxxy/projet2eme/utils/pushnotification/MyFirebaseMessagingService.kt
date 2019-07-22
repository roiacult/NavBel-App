package com.roacult.kero.oxxy.projet2eme.utils.pushnotification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        Log.e("errr", p0?.notification?.title ?:" Erroooooorrr")
    }
}
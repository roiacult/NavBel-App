package com.roacult.kero.oxxy.projet2eme.utils.extension

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.LayoutRes
import com.roacult.kero.oxxy.projet2eme.R


fun View.cancelTransition() {
    transitionName = null
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() { this.visibility = View.VISIBLE }

fun View.invisible() { this.visibility = View.GONE }

fun View.visible(b : Boolean){
    if(b) visible()
    else invisible()
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)

fun Button.loading(loading : Boolean){
    if(loading){
        setBackgroundResource(R.drawable.button_background_loading)
        setTextColor(Color.BLACK)
        isClickable = false
    }else {
        setBackgroundResource(R.drawable.button_background)
        setTextColor(Color.WHITE)
        isClickable = true
    }
    invalidate()
}
fun Button.setEnable(active : Boolean){
    this.isClickable = active
    if(active){
        setTextColor(Color.BLACK)
    }else{
        setTextColor(Color.GRAY)
    }
}


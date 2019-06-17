package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.chalengefragment

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class ChalengeViewPager @JvmOverloads constructor(context : Context  , attrs : AttributeSet? = null )
    : ViewPager(context,attrs) {

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}
package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.profile_fragment

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.instabug.library.view.ViewUtils
import com.roacult.kero.oxxy.projet2eme.utils.LOG_TAG

class ProfileBehavior(context :Context,attrs : AttributeSet) : AppBarLayout.ScrollingViewBehavior(context,attrs){

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        Log.v(LOG_TAG,"NestedScroll view behavior callback !!")
        super.onDependentViewChanged(parent, child, dependency)
        val totalScrollRange = (dependency as AppBarLayout).totalScrollRange
        if (totalScrollRange + dependency.y <=  ViewUtils.convertDpToPx(child.context,64F)/2) {
            val transiltion = ViewUtils.convertDpToPx(child.context,64F)/2 + ViewUtils.convertDpToPx(child.context,54F)
            child.translationY = transiltion.toFloat()
            Log.v(LOG_TAG,"he is translating by $transiltion")
        }

        return false
    }
}
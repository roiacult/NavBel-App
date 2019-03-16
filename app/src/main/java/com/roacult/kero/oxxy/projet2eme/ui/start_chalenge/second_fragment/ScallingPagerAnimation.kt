package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.second_fragment

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.second_fragment.CardPagerAdapter.Companion.MAX_ELEVATION_FACTOR


class ScallingPagerAnimation(private val curentPage : () -> Int,private val adapter: CardPagerAdapter,var enableScalling : Boolean)
    : ViewPager.OnPageChangeListener , ViewPager.PageTransformer{

    //enable scalling by default
    constructor(curentPage: () -> Int,adapter: CardPagerAdapter) : this(curentPage,adapter, true)

    private var lastOffset = 0f

    fun  setScalling(scale : Boolean){
        if (enableScalling && !scale) {
            // shrink main card
            val currentCard = adapter.getCardViewAt(curentPage())
            currentCard?.apply{
                this.animate().scaleY(1f)
                this.animate().scaleX(1f)
            }
        } else if (!enableScalling && scale) {
            // grow main card
            val currentCard = adapter.getCardViewAt(curentPage())
            currentCard?.apply {
                this.animate().scaleY(1.1f)
                this.animate().scaleX(1.1f)
            }
        }

        enableScalling = scale
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        val realCurrentPosition:Int
        val nextPosition:Int
        val baseElevation = adapter.baseElevation
        val realOffset:Float
        val goingLeft = lastOffset > positionOffset

        // If we're going backwards, onPageScrolled receives the last position
        // instead of the current one
        if (goingLeft){
            realCurrentPosition = position + 1
            nextPosition = position
            realOffset = 1 - positionOffset
        }
        else {
            nextPosition = position + 1
            realCurrentPosition = position
            realOffset = positionOffset
        }

        // Avoid crash when we are in the end or in the first
        if (nextPosition > adapter.count - 1 || realCurrentPosition > adapter.count - 1) return

        val currentCard = adapter.getCardViewAt(realCurrentPosition)

        // This might be null if a fragment is being used
        // and the views weren't created yet
        if (currentCard != null) {
            if (enableScalling) {
                currentCard.scaleX = (1 + 0.1 * (1 - realOffset)).toFloat()
                currentCard.scaleY = (1 + 0.1 * (1 - realOffset)).toFloat()
            }
            currentCard.cardElevation = baseElevation + (baseElevation * (MAX_ELEVATION_FACTOR - 1) * (1 - realOffset))
        }

        val nextCard = adapter.getCardViewAt(nextPosition)

        // We might be scrolling fast enough so that the next (or previous) card
        // was already destroyed or a fragment might not have been created yet
        if (nextCard != null) {
            if (enableScalling) {
                nextCard.scaleX = (1 + 0.1 * (realOffset)).toFloat()
                nextCard.scaleY = (1 + 0.1 * (realOffset)).toFloat()
            }
            nextCard.cardElevation = ((baseElevation + (baseElevation
                    * (MAX_ELEVATION_FACTOR - 1) * (realOffset))))
        }
        lastOffset = positionOffset
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageSelected(position: Int) {}

    override fun transformPage(page: View, position: Float) {}
}
package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.second_fragment

import androidx.viewpager.widget.ViewPager


class ScallingPagerAnimation(private val adapter: CardPagerAdapter,val setPage : (Int) ->Unit)
    : ViewPager.OnPageChangeListener {

    companion object {
        //this mean that card not selected will be her scalX and scalY 0.7
        //and selected will be 1
        const val PERCENTAGE_OF_SCALING_GROWING = 0.9f
        //this  will hold the precentage of growing elevation when scrolling
        const val PERCENTAGE_OF_ELEVATION_GROWING = 0.4f
    }

    private var lastOffset = 0f

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {


        val realCurrentPosition:Int
        val nextPosition:Int
        val realOffset:Float
        val baseElevation = adapter.baseElevation
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
        if (currentCard != null ) {
            //change the scalling
            currentCard.scaleX = PERCENTAGE_OF_SCALING_GROWING + (1- PERCENTAGE_OF_SCALING_GROWING)*(1-realOffset)
            currentCard.scaleY = PERCENTAGE_OF_SCALING_GROWING + (1- PERCENTAGE_OF_SCALING_GROWING)*(1-realOffset)
            //change the elevations
            currentCard.elevation = baseElevation + PERCENTAGE_OF_ELEVATION_GROWING*baseElevation*(1-realOffset)
        }

        val nextCard = adapter.getCardViewAt(nextPosition)

        // We might be scrolling fast enough so that the next (or previous) card
        // was already destroyed or a fragment might not have been created yet
        if (nextCard != null ) {
            //change the scalling
            nextCard.scaleX = PERCENTAGE_OF_SCALING_GROWING + (1- PERCENTAGE_OF_SCALING_GROWING)*realOffset
            nextCard.scaleY = PERCENTAGE_OF_SCALING_GROWING + (1- PERCENTAGE_OF_SCALING_GROWING)*realOffset
            //change elevation
            nextCard.elevation = baseElevation+ PERCENTAGE_OF_ELEVATION_GROWING*baseElevation*realOffset
        }
        lastOffset = positionOffset
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageSelected(position: Int) {setPage(position)}
}
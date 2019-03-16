package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.second_fragment

import android.view.ViewGroup
import androidx.cardview.widget.CardView
import android.view.LayoutInflater
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import com.roacult.kero.oxxy.domain.modules.Question
import com.roacult.kero.oxxy.projet2eme.R
import kotlinx.android.synthetic.main.start_chalenge_pager_card.view.*


class CardPagerAdapter constructor(private val questions : ArrayList<Question>,private val views : ArrayList<CardView?>,private val getCurentPos: () ->Int) : PagerAdapter() {

    //constructor for creating emmpty adapter
    constructor(getCurentPos: () ->Int) : this(ArrayList<Question>(),ArrayList<CardView?>(),getCurentPos)

    /**
     * this field will hold the elevation
     * sets on XML
     * */
    var baseElevation = -1f

    fun getCardViewAt(position: Int): CardView? = views.getOrNull(position)

    fun  addAllCards(data : List<Question>){
        views.addAll(data.map { null })
        questions.addAll(data)
    }

    fun addCard(data : Question){
        views.add(null)
        questions.add(data)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.start_chalenge_pager_card,container,false)
        container.addView(view)
        upDateView(view,questions[position])
        val cardView = view.pager_card
        if(baseElevation == -1f) baseElevation =cardView.elevation
            if(position != getCurentPos()) {
            cardView.scaleX = ScallingPagerAnimation.PERCENTAGE_OF_SCALING_GROWING
            cardView.scaleY = ScallingPagerAnimation.PERCENTAGE_OF_SCALING_GROWING
        }
        views[position] = cardView
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, card : Any) {
        container.removeView( card as View)
        views[position] = null
    }

    override fun getCount(): Int  = questions.size

    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj

    private fun upDateView(view: View, question: Question) {
        //TODO don't forget update view here
        view.question.text = question.question
    }
}

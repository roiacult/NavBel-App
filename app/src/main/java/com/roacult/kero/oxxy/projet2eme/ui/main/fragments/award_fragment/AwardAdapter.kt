package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.award_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.roacult.kero.oxxy.projet2eme.R
import com.squareup.picasso.Picasso

class AwardAdapter(private val images : List<String>) : PagerAdapter(){

    override fun isViewFromObject(view: View, any: Any) = view == any

    override fun getCount() = images.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.main_award_card,container,false)
        container.addView(view)
        Picasso.get().load(images[position]).into(view.findViewById(R.id.image) as ImageView)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, card: Any) {
        container.removeView(card as View)
    }
}
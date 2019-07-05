package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.profile_fragment

import android.graphics.Color
import android.view.View
import co.revely.gradient.RevelyGradient
import com.roacult.kero.oxxy.domain.modules.SolvedChalenge
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseRecyclerAdapter
import com.roacult.kero.oxxy.projet2eme.databinding.MainProfileCardBinding
import com.squareup.picasso.Picasso

class ChallengeAdapter : BaseRecyclerAdapter<SolvedChalenge,MainProfileCardBinding>(SolvedChalenge::class.java, R.layout.main_profile_card) {

    override fun areItemsTheSame(item1: SolvedChalenge, item2: SolvedChalenge) = item1.id == item2.id


    override fun compare(o1: SolvedChalenge, o2: SolvedChalenge) = 0

    override fun areContentsTheSame(oldItem: SolvedChalenge, newItem: SolvedChalenge)=oldItem == newItem

    override fun upDateView(item: SolvedChalenge, binding: MainProfileCardBinding) {
        binding.moduleName.text = item.moduleName
        binding.modulePoint.text  = item.result.toString()
        Picasso.get().load(item.imageUrl).into(binding.challengeImage)
        val array = if(item.result<=0) intArrayOf(Color.parseColor("#e52d27"),Color.parseColor("#b31217"))
        else intArrayOf(Color.parseColor("#11998e"),Color.parseColor("#38ef7d"))
        RevelyGradient
            .linear()
            .colors(array)
            .alpha(0.8f)
            .angle(40F)
            .onBackgroundOf(binding.view8)
    }

    override fun onClickOnItem(item: SolvedChalenge, view: View?, binding: MainProfileCardBinding, adapterPostion: Int) {
        //TODO show result fragment
    }
}
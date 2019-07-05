package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.profile_fragment

import android.view.View
import com.roacult.kero.oxxy.domain.modules.SolvedChalenge
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseRecyclerAdapter
import com.roacult.kero.oxxy.projet2eme.databinding.MainProfileCardBinding

class ChallengeAdapter : BaseRecyclerAdapter<SolvedChalenge,MainProfileCardBinding>(SolvedChalenge::class.java, R.layout.main_profile_card) {

    override fun areItemsTheSame(item1: SolvedChalenge, item2: SolvedChalenge) = item1.id == item2.id


    override fun compare(o1: SolvedChalenge, o2: SolvedChalenge) = 0

    override fun areContentsTheSame(oldItem: SolvedChalenge, newItem: SolvedChalenge)=oldItem == newItem

    override fun upDateView(item: SolvedChalenge, binding: MainProfileCardBinding) {
        binding.moduleName = item.
    }

    override fun onClickOnItem(item: SolvedChalenge, view: View?, binding: MainProfileCardBinding, adapterPostion: Int) {

    }
}
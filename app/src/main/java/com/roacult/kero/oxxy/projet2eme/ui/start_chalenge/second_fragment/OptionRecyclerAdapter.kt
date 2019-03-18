package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.second_fragment

import android.view.View
import com.roacult.kero.oxxy.projet2eme.base.BaseRecyclerAdapter
import com.roacult.kero.oxxy.projet2eme.databinding.StartChalengeFragment2CardBinding
import com.roacult.kero.oxxy.projet2eme.R

class OptionRecyclerAdapter
    : BaseRecyclerAdapter<String, StartChalengeFragment2CardBinding>(String::class.java,R.layout.start_chalenge_fragment2_card) {
    override fun areItemsTheSame(item1: String, item2: String) = item1 == item2

    override fun compare(o1: String, o2: String)=o1.compareTo(o2)

    override fun areContentsTheSame(oldItem: String, newItem: String) =oldItem == newItem

    override fun upDateView(item: String, binding: StartChalengeFragment2CardBinding) {
        binding.checkBox.setText(item)
    }

    override fun onClickOnItem(item: String, view: View?, binding: StartChalengeFragment2CardBinding, adapterPostion: Int) {}
}
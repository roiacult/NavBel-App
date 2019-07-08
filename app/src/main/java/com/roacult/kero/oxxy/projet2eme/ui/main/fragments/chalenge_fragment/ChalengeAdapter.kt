package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.chalenge_fragment

import android.view.View
import com.roacult.kero.oxxy.projet2eme.base.BaseRecyclerAdapter
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.databinding.MainChalengesCardBinding
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.squareup.picasso.Picasso

class ChalengeAdapter(val viewModel : ChalengeViewModel,val startChalenge : (ChalengeGlobale) ->Unit )
    : BaseRecyclerAdapter<ChalengeGlobale, MainChalengesCardBinding>(ChalengeGlobale::class.java, R.layout.main_chalenges_card){

    var filter : Filter = Filter()
    set(value) {
        field = value
        viewModel.withState {
            addAll((it.getChalenges as? Success)?.invoke() ?: ArrayList())
        }
    }

    override fun areItemsTheSame(item1: ChalengeGlobale, item2: ChalengeGlobale) = item1.id == item2.id

    override fun compare(o1: ChalengeGlobale, o2: ChalengeGlobale) = o1.id.compareTo(o2.id)

    override fun areContentsTheSame(oldItem: ChalengeGlobale, newItem: ChalengeGlobale)= oldItem.module == newItem.module &&
            oldItem.nbOfQuestions == newItem.nbOfQuestions &&
            oldItem.nbPersonSolved == newItem.nbPersonSolved &&
            oldItem.story == newItem.story &&
            oldItem.point == newItem.point

    override fun upDateView(item: ChalengeGlobale, binding: MainChalengesCardBinding) {
        binding.textView7.text = item.story
        binding.textView6.text = item.module
        binding.start.setOnClickListener{ startChalenge(item) }
        binding.solved.text = "${item.nbPersonSolved}/5"
        binding.point.text = "${item.point} points"
        binding.nbQuestion.text = "${item.nbOfQuestions} questions"
        binding.arrow.rotation = 180f
        if(item.url.isNotEmpty()) Picasso.get().load(item.url).into(binding.chalengeImg)
    }

    override fun addAll(items: List<ChalengeGlobale>) {
        val newList = filter.filter(items)
        super.addAll(newList)
    }

    override fun onClickOnItem(item: ChalengeGlobale, view: View?, binding: MainChalengesCardBinding, adapterPostion : Int) {
        val isCollapsed = binding.expanded.isExpanded
        binding.arrow.animate().apply {
            rotation(if(isCollapsed)180f else 0f)
            start()
        }
        if(isCollapsed) binding.expanded.collapse()
        else binding.expanded.expand()
    }


}
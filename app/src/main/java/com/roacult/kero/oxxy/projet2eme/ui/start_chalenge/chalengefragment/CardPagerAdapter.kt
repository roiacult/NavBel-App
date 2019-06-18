package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.chalengefragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import androidx.core.widget.CompoundButtonCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.roacult.kero.oxxy.domain.modules.Option
import com.roacult.kero.oxxy.domain.modules.Question
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseRecyclerAdapter
import com.roacult.kero.oxxy.projet2eme.databinding.StartChalengeFragment2CardBinding
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.StartChelngeViewModel
import kotlinx.android.synthetic.main.start_chalenge_pager_card.view.*


class CardPagerAdapter constructor(private val questions : ArrayList<Question>,
                                   private val views : ArrayList<CardView?>,
                                   private val getCurentPos: () ->Int ,
                                   private val viewModel :StartChelngeViewModel) : PagerAdapter() {

    //constructor for creating emmpty adapter
    constructor(getCurentPos: () ->Int,vieModel :StartChelngeViewModel) : this(ArrayList<Question>(),ArrayList<CardView?>(),getCurentPos,vieModel )

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
        view.question.text = question.question
        view.options_recycler.adapter = OptionRecyclerAdapter(question,viewModel)
        view.options_recycler.layoutManager = LinearLayoutManager(view.context)
        view.options_recycler.setHasFixedSize(true)
    }

    class OptionRecyclerAdapter(val question : Question,val viewModel: StartChelngeViewModel)
        : BaseRecyclerAdapter<Option, StartChalengeFragment2CardBinding>(Option::class.java,R.layout.start_chalenge_fragment2_card) {

        private var lastChecked : CheckBox? = null
        private var selectedOptionId = -1L
        var writeAnswer = -1L

        init{ addAll(question.options) }

        override fun areItemsTheSame(item1: Option, item2: Option) = item1.id == item2.id

        //we don't want to sort options so we retur 0
        //means all options have same priority
        override fun compare(o1: Option, o2: Option) = 0

        override fun areContentsTheSame(oldItem: Option, newItem: Option) = oldItem.option == newItem.option

        override fun upDateView(item: Option, binding: StartChalengeFragment2CardBinding) {
            binding.checkBox.text = item.option

            if(viewModel.userAnswers[question.id]?.optionId == item.id){
                //if user alredy select this option
                lastChecked = binding.checkBox
                lastChecked?.isChecked = true
            }

            if (writeAnswer != -1L ){
                binding.checkBox.setOnCheckedChangeListener(null)
                binding.checkBox.isEnabled = false
                if( item.id == writeAnswer ){
                    binding.checkBox.isChecked = true
                    CompoundButtonCompat.setButtonTintList(binding.checkBox, ColorStateList.valueOf(Color.GREEN))
                }else if(item.id == selectedOptionId ) {
                    binding.checkBox.isChecked = true
                    CompoundButtonCompat.setButtonTintList(binding.checkBox, ColorStateList.valueOf(Color.RED))
                }
            }else {
                binding.checkBox.setOnCheckedChangeListener { _,_ ->
                    if(binding.checkBox == lastChecked){
                        //this mean user deselct his answer
                        //set that user didn't answer this yet
                        viewModel.setAnswer(question,-1L)
                        viewModel.curentQuestion = null
                        lastChecked = null
                        selectedOptionId = -1L
                        return@setOnCheckedChangeListener
                    }
                    viewModel.setAnswer(question,item.id)
                    lastChecked?.isChecked = false
                    lastChecked = binding.checkBox
                    selectedOptionId = item.id
                }
            }
        }

        override fun onClickOnItem(item: Option, view: View?, binding: StartChalengeFragment2CardBinding, adapterPostion: Int) {}
    }
}
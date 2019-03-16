package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.first_fragment

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.view.View
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseRecyclerAdapter
import com.roacult.kero.oxxy.projet2eme.databinding.StartChalenngeCard1Binding


class ResourceAdapter(val checkPermition : ()-> Boolean,val asqPermission: () -> Unit ) : BaseRecyclerAdapter<Resource,StartChalenngeCard1Binding>(Resource::class.java,R.layout.start_chalennge_card1){
    override fun areItemsTheSame(item1: Resource, item2: Resource) = item1 == item2

    override fun compare(o1: Resource, o2: Resource) = o1.content.first.compareTo(o2.content.first)

    override fun areContentsTheSame(oldItem: Resource, newItem: Resource) = oldItem.content.second == newItem.content.second

    override fun upDateView(item: Resource, binding: StartChalenngeCard1Binding) {
        binding.textView14.text = item.content.first
        binding.imageView6.setOnClickListener{
            binding.root.context.startActivity(Intent(Intent.ACTION_VIEW,Uri.parse(item.content.second)))
        }
    }

    override fun onClickOnItem(item: Resource, view: View?, binding: StartChalenngeCard1Binding, adapterPostion: Int) {
        //we will open pdf reader for user
    }
}

data class Resource (val content : Pair<String,String>)
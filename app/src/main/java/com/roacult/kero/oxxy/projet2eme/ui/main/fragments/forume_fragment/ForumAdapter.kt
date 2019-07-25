package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.forume_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.roacult.kero.oxxy.domain.modules.Post
import com.roacult.kero.oxxy.projet2eme.R
import com.tbuonomo.creativeviewpager.adapter.CreativePagerAdapter
import com.roacult.kero.oxxy.projet2eme.databinding.MainForumPostBinding
import com.roacult.kero.oxxy.projet2eme.ui.postdettailes.PostDetaillesActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_forum_post_header.view.*

class ForumAdapter(private val posts : List<Post>)  : CreativePagerAdapter {

    override fun getCount() = posts.size

    override fun instantiateContentItem(inflater: LayoutInflater, container: ViewGroup, position: Int): View {
        val view = inflater.inflate(R.layout.main_forum_post_header,container,false)
        updateHeaderItem(view,posts[position])
        return view
    }

    override fun instantiateHeaderItem(inflater: LayoutInflater, container: ViewGroup, position: Int): View {
        val binding : MainForumPostBinding = DataBindingUtil.inflate(inflater,R.layout.main_forum_post,container,false)
        updateContentItem(binding , posts[position])
        return binding.root
    }

    private fun updateContentItem(binding : MainForumPostBinding , post : Post) {
        if( post.postImage != null ) Picasso.get().load(post.postImage).into(binding.postImage)
        else {
            //TODO load empty state image
        }
        binding.userName.text = post.userName
        binding.userYear.text = post.userYear
        binding.postDesc.text = post.postDesc
        binding.button2.setOnClickListener{
            binding.root.context.startActivity(PostDetaillesActivity.getIntent(binding.root.context,post))
        }
    }

    private fun updateHeaderItem(view: View, post: Post) {
        if(post.userImage.isNotEmpty()) Picasso.get().load(post.userImage).into(view.userImage)
        else view.userImage.setImageResource(R.drawable.save_info_holder)
    }

}
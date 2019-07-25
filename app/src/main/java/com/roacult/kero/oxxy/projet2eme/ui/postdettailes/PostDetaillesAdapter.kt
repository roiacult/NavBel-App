package com.roacult.kero.oxxy.projet2eme.ui.postdettailes

import android.view.View
import com.roacult.kero.oxxy.domain.modules.Comment
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseRecyclerAdapter
import com.roacult.kero.oxxy.projet2eme.databinding.PostCommentBinding
import com.squareup.picasso.Picasso


class PostDetaillesAdapter : BaseRecyclerAdapter<Comment,PostCommentBinding>(Comment::class.java, R.layout.post_comment) {

    override fun areItemsTheSame(item1: Comment, item2: Comment) = item1 == item2

    override fun compare(o1: Comment, o2: Comment) = 0

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment) = oldItem == newItem

    override fun upDateView(item: Comment, binding: PostCommentBinding) {
        if(item.userImage.isNotEmpty()) Picasso.get().load(item.userImage).into(binding.userImage)
        binding.comentContent.text = item.commentContent
        binding.userName.text = item.userName
        binding.userYear.text = if(item.userYear<3) item.userYear.toString() + " Cpi"
        else (item.userYear-2).toString() + " CS"
    }

    override fun onClickOnItem(item: Comment, view: View?, binding: PostCommentBinding, adapterPostion: Int) {}
}
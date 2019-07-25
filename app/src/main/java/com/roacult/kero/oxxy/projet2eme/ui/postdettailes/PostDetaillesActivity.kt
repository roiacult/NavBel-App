package com.roacult.kero.oxxy.projet2eme.ui.postdettailes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.roacult.kero.oxxy.domain.modules.Post
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseActivity
import com.roacult.kero.oxxy.projet2eme.utils.extension.inTransaction

class PostDetaillesActivity : BaseActivity() {

    companion object {
        const val POST_ID = "com.roacult.kero.oxxy.projet2eme:post_id"
        const val POST_IMAGE = "com.roacult.kero.oxxy.projet2eme:post_image"
        const val POST_DESC = "com.roacult.kero.oxxy.projet2eme:post_desc"
        const val POST_USER_NAME = "com.roacult.kero.oxxy.projet2eme:post_user_name"
        const val POST_USER_YEAR = "com.roacult.kero.oxxy.projet2eme:post_user_year"
        const val POST_USER_IMAGE = "com.roacult.kero.oxxy.projet2eme:post_user_image"
        const val POST_USER_ID = "com.roacult.kero.oxxy.projet2eme:post_user_id"

        fun getIntent(context: Context , post : Post) : Intent {
            return Intent(context,PostDetaillesActivity::class.java).apply {
                this.putExtras( Bundle().apply {
                    putExtra(POST_ID,post.postId)
                    putExtra(POST_IMAGE,post.postImage)
                    putExtra(POST_DESC,post.postDesc)
                    putExtra(POST_USER_NAME,post.userName)
                    putExtra(POST_USER_IMAGE,post.userImage)
                    putExtra(POST_USER_YEAR,post.userYear)
                    putExtra(POST_USER_ID,post.userId)
                })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_fragment_activity)

        if(savedInstanceState == null ) {
            supportFragmentManager.inTransaction{
                add(R.id.fragment_container,PostDetaillesFragment.getInstance(intent.extras))
            }
        }
    }
}
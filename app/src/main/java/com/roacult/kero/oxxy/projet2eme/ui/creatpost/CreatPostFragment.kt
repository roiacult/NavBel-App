package com.roacult.kero.oxxy.projet2eme.ui.creatpost

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.domain.modules.User
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.PostBinding
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.extension.visible
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import java.io.File


class CreatPostFragment : BaseFragment() {

    companion object {
        fun getInstance()  = CreatPostFragment()
    }

    private lateinit var binding : PostBinding
    private val viewModel by lazy {
        ViewModelProviders.of(this,viewModelFactory)[CreatPostViewModel::class.java]
    }
    private var uri : String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.post,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        viewModel.observe(this){
            handleUserInfo(it.userInfoOp)
            it.shareOp?.apply { handleSharingPostResult(this)}
        }
    }

    private fun handleSharingPostResult(shareOp: Async<None>) {
        when(shareOp) {
            is Loading -> showLoading(true)
            is Fail<*,*> -> {
                showLoading(false)
//                when(shareOp.error){
//                  TODO handle failures
//                }

            }
            is Success -> {
                showLoading(false)
                showToast(getString(R.string.post_succ))
                activity?.setResult(RESULT_OK)
                activity?.finish()
            }
        }
    }

    private fun showLoading( show : Boolean ) {
        binding.progressBar4.visible(show)
        binding.uploadImage.visible(!show)
    }

    private fun handleUserInfo(userInfoOp: Async<User>) {
        if(userInfoOp is Success) {
            val user = userInfoOp()
            if(user.picture?.isNotEmpty() == true ) Picasso.get().load(user.picture!!).into(binding.circleImageView2)
            binding.userName.text = user.fname+" ,"+user.lName
            binding.userYear.text = if(user.year<3) user.year.toString() + " Cpi"
            else (user.year - 2 ).toString() +" Cs"
        }
    }

    private fun initViews() {
        setHasOptionsMenu(true)
        val act = (activity as? AppCompatActivity)
        act?.setSupportActionBar(binding.toolbar3)
        act?.supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.uploadImage.setOnClickListener {
            CropImage.activity().start(context!!,this)
        }
        viewModel.imageUri?.apply{
            binding.uploadImage.setImageURI(Uri.fromFile(File(this)))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.crat_post_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> activity?.finish()
            R.id.share -> {
                sharePost()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE &&  resultCode == RESULT_OK) {
            val ur = CropImage.getActivityResult(data).uri
            binding.uploadImage.setImageURI(ur)
            uri = ur.path
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        saveDataToViewModel()
    }

    private fun sharePost() {
        if(binding.postDesc.text.toString().isEmpty() && uri == null){
            onError(R.string.post_error)
            return
        }
        saveDataToViewModel()
        viewModel.sharePost()
    }

    private fun saveDataToViewModel(){
        viewModel.desc = binding.postDesc.text.toString()
        viewModel.imageUri = uri
    }
}

package com.roacult.kero.oxxy.projet2eme.ui.setting

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Picture
import com.roacult.kero.oxxy.projet2eme.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.SettingsBinding
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.roacult.kero.oxxy.projet2eme.utils.extension.visible

class SettingFragment : BaseFragment(){

    companion object {
        fun getInstance(extra : Bundle) = SettingFragment().apply {
            arguments = extra
        }
    }

    private lateinit var binding : SettingsBinding
    private val viewModel by lazy { ViewModelProviders.of(this,viewModelFactory)[SettingViewModel::class.java] }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SettingsBinding.inflate(inflater,container,false)

        saveData()
        initViews()

        viewModel.observe(this){
            handleSaveOp(it.saveOp)
        }

        return binding.root
    }

    private fun handleSaveOp(saveOp: Async<None>?) {
        when(saveOp){
            is Loading ->showLoading(true)
            is Fail<*,*> ->{
                showLoading(false)
                when(saveOp.error) {
                    Failure.UpDateUserInfo.OperationFailed ->  onError(R.string.error)
                }
            }
            is Success -> {
                showLoading(false)
                showMessage(R.string.saved_succ)
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.circleImageView.isClickable = !show
        binding.linearLayout7.visible(!show)
        binding.progressBar3.visible(show)
        binding.save.alpha = if(show) 0.5F else 1F
    }

    private fun saveData() {
        val fname = arguments!!.getString(SettingActivity.USER_INFO_FNAME)!!
        val lname = arguments!!.getString(SettingActivity.USER_INFO_LNAME)!!
        val picture = arguments!!.getString(SettingActivity.USER_INFO_PICTURE)
        val public = arguments!!.getBoolean(SettingActivity.USER_INFO_PUBLIC)
        val descri = arguments!!.getString(SettingActivity.USER_INFO_DESCRI)
        viewModel.saveInfo(fname,lname,picture,descri,public)
    }

    private fun initViews() {
        if(viewModel.picture != null) Picasso.get().load(viewModel.picture!!).into(binding.circleImageView)
        binding.fname.setText(viewModel.fName)
        binding.lname.setText(viewModel.lName)
        binding.desc.setText(viewModel.description)
        binding.switchBtn.isChecked = !viewModel.public
        binding.publicBtn.setOnClickListener{
            binding.switchBtn.isChecked = viewModel.public
            viewModel.public = !viewModel.public
        }
        binding.save.setOnClickListener {
            saveToViewModel()
            viewModel.save()
        }
        binding.circleImageView.setOnClickListener {
            CropImage.activity()
                .setCropShape ( CropImageView.CropShape.OVAL )
                .setAspectRatio(1,1 )
                .start(context!!,this)
        }
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar2)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.settings)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                val result = CropImage.getActivityResult(data)
                viewModel.picture = result.uri.toString()
                binding.circleImageView.setImageURI(result.uri)
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        saveToViewModel()
    }

    private fun saveToViewModel(){
        viewModel.lName = binding.lname.text.toString()
        viewModel.fName = binding.fname.text.toString()
        viewModel.public = !binding.switchBtn.isChecked
        viewModel.description  =binding.desc.text.toString()
    }
}
package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_saveinfo

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.SaveInfoFragmentBinding
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.extension.visible

const val SAVEINFO_FIRST_NAME = "first_name"
const val SAVEINFO_LAST_NAME = "last_name"
const val SAVEINFO_YEAR = "year"

class SaveInfoFragment : BaseFragment() {

    companion object {fun getInstance() = SaveInfoFragment()}

    private val viewModel : SaveInfoViewModel by lazy { ViewModelProviders.of(this,viewModelFactory)[SaveInfoViewModel::class.java] }
    private val callback : CallbackToViewModel by lazy {viewModel}
    private lateinit var binding : SaveInfoFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.save_info_fragment,container,false)
        if(callback.isItFirstTime()) {saveDataToViewModel()}

        viewModel.observe(this){
            it.imageUrl?.apply { showImage(this) }
            it.submitOperation?.getContentIfNotHandled()?.apply{handleSubmitOperation(this)}
        }

        binding.signeInImage.setOnClickListener{startPickingImage()}
        binding.signeInNextBtn.setOnClickListener{performeSubmit()}

        return binding.root
    }

    private fun performeSubmit() {

        val name= binding.signeInName.text.toString()
        val lastName = binding.signeInPrenom.text.toString()
        val pass = binding.signeInpass.text.toString()
        val repeatPass = binding.signeInrepeatpass.text.toString()
        if(TextUtils.isEmpty(name)) {onError(R.string.name_empty) ;return}
        if(TextUtils.isEmpty(lastName)) {onError(R.string.last_name_empty); return}
        if(pass.length<8) {onError(R.string.pass_short); return}
        if(pass != repeatPass) {onError(R.string.confirm_pass); return}

    }

    private fun startPickingImage() {
        CropImage.activity()
            .setCropShape ( CropImageView.CropShape.OVAL )
            .setAspectRatio(1,1 )
            .start(context!!,this)
    }

    private fun saveDataToViewModel() {
        val name = arguments!!.getString(SAVEINFO_FIRST_NAME)!!
        val lastName = arguments!!.getString(SAVEINFO_LAST_NAME)!!
        val year = arguments!!.getString(SAVEINFO_YEAR)!!
        binding.signeInName.setText(name)
        binding.signeInPrenom.setText(lastName)
        //TODO add year
        binding.year.setText(year)
        callback.setInfo(name,lastName,year.toInt())
    }

    private fun handleSubmitOperation(operation: Async<None>) {
        when (operation){
            is Loading -> showLoading(true)
            is Success -> {
                //TODO go to main
            }
            is Fail<*> -> {
                //TODO handle difrent faillers
            }
            else -> showLoading(false)
        }
    }

    private fun showLoading(show: Boolean) {
        binding.linearLayout2.visible(!show)
        binding.saveInfo.visible(show)
    }

    private fun showImage(image: String) { binding.signeInImage.setImageURI(Uri.fromFile(File( image ))) }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if(resultCode == RESULT_OK){
                val result = CropImage.getActivityResult(data)
                callback.setImage(result.uri.path)
            }
        }
    }

    interface CallbackToViewModel{
        fun isItFirstTime() : Boolean
        fun setInfo(fName : String ,Lname : String , year : Int)
        fun setImage(url : String)
        fun submit(name : String, lastName : String, password : String)
    }
}

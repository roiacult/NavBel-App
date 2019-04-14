package com.roacult.kero.oxxy.projet2eme.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.SettingsBinding
import com.squareup.picasso.Picasso

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

        return binding.root
    }

    private fun saveData() {
        val fname = arguments!!.getString(SettingActivity.USER_INFO_FNAME)!!
        val lname = arguments!!.getString(SettingActivity.USER_INFO_LNAME)!!
        val picture = arguments!!.getString(SettingActivity.USER_INFO_PICTURE)
        val public = arguments!!.getBoolean(SettingActivity.USER_INFO_PUBLIC)
        viewModel.saveInfo(fname,lname,picture,public)
    }

    private fun initViews() {
        if(viewModel.picture != null) Picasso.get().load(viewModel.picture!!).into(binding.circleImageView)
        binding.fname.setText(viewModel.fName)
        binding.lname.setText(viewModel.lName)
        binding.switchBtn.isChecked = !viewModel.public
        binding.publicBtn.setOnClickListener{
            //TODO change acount public state
            viewModel.public = !viewModel.public
            binding.switchBtn.isChecked = viewModel.public
        }
    }
}
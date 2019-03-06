package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.reset_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.ResetPasswordFragmentBinding

class ResetPasswordFragment : BaseFragment() {

    companion object { fun  getInstance() = ResetPasswordFragment() }

    private lateinit var binding : ResetPasswordFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.reset_password_fragment,container,false)


        binding.submit.setOnClickListener{
            binding.motion.transitionToState(R.id.end)
        }

        return binding.root
    }

    fun transiteToStart(){
        binding.motion.transitionToState(R.id.start)
    }
}
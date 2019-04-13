package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.profile_fragment

import com.roacult.kero.oxxy.domain.modules.User
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.base.State
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Loading

class ProfileViewModel :BaseViewModel<ProfileState>(ProfileState()){

}

data class ProfileState(val usrInfo : Async<User> = Loading()) :State
package com.roacult.kero.oxxy.projet2eme.ui.registration_feature.fragment_saveinfo

import com.roacult.kero.oxxy.domain.interactors.None
import com.roacult.kero.oxxy.projet2eme.base.State
import com.roacult.kero.oxxy.projet2eme.utils.Event
import com.roacult.kero.oxxy.projet2eme.utils.Async

data class SaveInfoState(val imageUrl : String? , val submitOperation : Event<Async<None>>?) : State
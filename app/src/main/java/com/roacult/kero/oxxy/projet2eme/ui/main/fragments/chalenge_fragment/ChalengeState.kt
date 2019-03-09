package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.chalenge_fragment

import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import com.roacult.kero.oxxy.projet2eme.base.State
import com.roacult.kero.oxxy.projet2eme.utils.Async

data class ChalengeState(val getChalenges  : Async<List<ChalengeGlobale>>?)  : State
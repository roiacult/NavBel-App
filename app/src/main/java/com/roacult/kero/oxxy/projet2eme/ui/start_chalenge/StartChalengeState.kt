package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge

import com.roacult.kero.oxxy.domain.modules.ChalengeDetailles
import com.roacult.kero.oxxy.projet2eme.base.State
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Event

data class StartChalengeState(val selectedFragment : Event<Int>,
                              val getChalengeDetailles  : Async<ChalengeDetailles>,
                              val page : Event<Int>,
                              val solvedBy : Int,
                              val questionSolved :Int) : State

const val STARTCHALENGE_FRAGMENT1 = 0
const val STARTCHALENGE_FRAGMENT2 = 1

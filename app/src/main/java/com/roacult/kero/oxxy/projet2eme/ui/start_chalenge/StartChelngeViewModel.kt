package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.interactors.CheckChallenge
import com.roacult.kero.oxxy.domain.interactors.GetChalengeDetaills
import com.roacult.kero.oxxy.domain.interactors.SetUserTry
import com.roacult.kero.oxxy.domain.interactors.launchInteractor
import com.roacult.kero.oxxy.domain.modules.ChalengeDetailles
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import com.roacult.kero.oxxy.domain.modules.Question
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.first_fragment.FirstFragment
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.Event
import com.roacult.kero.oxxy.projet2eme.utils.extension.questionSolved
import javax.inject.Inject

class StartChelngeViewModel @Inject constructor(private val useCase : GetChalengeDetaills,
                                                private val tryUseCase : SetUserTry,
                                                private val checkUseCase : CheckChallenge) :
    BaseViewModel<StartChalengeState>(StartChalengeState(Event(STARTCHALENGE_FRAGMENT1),Loading(),null,Event(0),0,0)),
    FirstFragment.CallbackToViewModel {

    lateinit var chalengeGlobale: ChalengeGlobale
    val answers = mutableMapOf<Long,Long>()

    var firstTime = true
    var lastTime : Int = 0
    var size = 0

    override fun isItFirstTime() = firstTime

    override fun saveData(chalengeGlobale: ChalengeGlobale) {
        //this methode will be called
        //when user first entre
        //get data sended from main activity
        this.chalengeGlobale = chalengeGlobale
        firstTime = false
        fetchData()
    }

    override fun fetchData() {
        scope.launchInteractor(useCase,chalengeGlobale.id){
            it.either(::handleFailure,::handleSuccesss)
        }
    }


    private fun handleSuccesss(chalengeDetailles: ChalengeDetailles) {
        lastTime = chalengeDetailles.time
        size = chalengeDetailles.questions.size
        //init map of answers
        for(question in chalengeDetailles.questions){ answers[question.id] = -1 }
        setState { copy(getChalengeDetailles = Success(chalengeDetailles),questionSolved =answers.questionSolved ) }
    }

    private fun handleFailure(getChalengeDetailsFailure: Failure.GetChalengeDetailsFailure) {
        setState { copy(getChalengeDetailles = Fail(getChalengeDetailsFailure)) }
    }

    override fun startChalenge() {
        //set request to start fragment
        setState{copy(startChalenge = Loading())}
        scope.launchInteractor(tryUseCase,chalengeGlobale.id){
            it.either({
                setState{copy(startChalenge = Fail(it))}
            },{
                setState{copy(startChalenge = Success(it))}
            })
        }
    }

    override fun start() {
        //go to second fragment
        setState { copy(selectedFragment =Event( STARTCHALENGE_FRAGMENT2 )) }
        //now we should observe how many user solved
        //this chalenge till now
        launchSubjectInteractor(checkUseCase,chalengeGlobale.id,::CheckOnNext,{},::CheckOnComplte)

    }
    private fun CheckOnNext(numbre: Int) {
        setState { copy(solvedBy = numbre) }
    }

    private fun CheckOnComplte() {
        setState { copy(solvedBy = 5) }
    }

    fun setPage(page : Int){
        //questionCard -> page
        setState{copy(page = Event(page))}
    }

    fun setAnswer(questionsId :Long ,optionId :Long){
        answers[questionsId] = optionId
        setState { copy(questionSolved = answers.questionSolved) }
    }
}
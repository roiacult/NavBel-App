package com.roacult.kero.oxxy.projet2eme.ui.start_chalenge

import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.interactors.*
import com.roacult.kero.oxxy.domain.modules.ChalengeDetailles
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import com.roacult.kero.oxxy.domain.modules.Question
import com.roacult.kero.oxxy.projet2eme.base.BaseViewModel
import com.roacult.kero.oxxy.projet2eme.ui.start_chalenge.resourefragment.ResourceFragment
import com.roacult.kero.oxxy.projet2eme.utils.*
import com.roacult.kero.oxxy.projet2eme.utils.extension.questionSolved
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class StartChelngeViewModel @Inject constructor(
    private val useCase : GetChalengeDetaills,
    private val tryUseCase : SetUserTry,
    private val checkUseCase : CheckChallenge,
    private val submit : SubmitAnswer
) : BaseViewModel<StartChalengeState>(
    StartChalengeState(Event(STARTCHALENGE_RESOURCE),Loading(),null,Event(0),0,0,null)
), ResourceFragment.CallbackToViewModel {

    lateinit var chalengeGlobale: ChalengeGlobale
    val userAnswers = mutableMapOf<Long,Long>()
    lateinit var writeAnswers : Map<Long,Long>
    private var dispos : Disposable? = null
    lateinit var submitionResult  :SubmitionResult
    var time = 0L

    var curentQuestion : Question? = null
    var firstTime = true
    var lastTime : Long = 0
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
        lastTime = chalengeDetailles.questions.getOrNull(0)?.time ?: 0L
        size = chalengeDetailles.questions.size
        //init map of userAnswers
        for(question in chalengeDetailles.questions){ userAnswers[question.id] = -1 }
        setState { copy(getChalengeDetailles = Success(chalengeDetailles),questionSolved =userAnswers.questionSolved ) }
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
                writeAnswers = it
                setState{copy(startChalenge = Success(None()))}
            })
        }
    }

    override fun start() {
        //go to second fragment
        withState {
            val selectedFragmeent = it.selectedFragment.peekContent()
            if(selectedFragmeent != STARTCHALENGE_CHALENGE){
                setState { copy(selectedFragment =Event( STARTCHALENGE_CHALENGE )) }
            }
        }
        //now we should observe how many user solved
        //this chalenge till now
        dispos = launchObservableCompletedInteractor(checkUseCase,chalengeGlobale.id,{},::CheckOnNext,::CheckOnComplte)
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

    fun setAnswer(questions : Question ,optionId :Long){
        curentQuestion = questions
        userAnswers[questions.id] = optionId
        setState { copy(questionSolved = userAnswers.questionSolved) }
    }

    override fun onCleared() {
        checkUseCase.clear()
        super.onCleared()
    }

    fun submitAnswer(time : Long) {
        this.time = time
        setState { copy(submition = Loading()) }
        var challengeTime : Long = 0L
        withState{
            challengeTime = (it.getChalengeDetailles as? Success)?.invoke()?.time?.toLong() ?: 2000
        }
        val timePercent = challengeTime.toFloat()/time.toFloat()
        scope.launchInteractor(submit,SubmitionParam(userAnswers,timePercent,chalengeGlobale.id)){
            it.either({
                setState{copy(submition = Fail(it))}
            },{
                submitionResult = it
                setState { copy(submition = Success(it)) }
            })
        }
    }

    fun gotoResultFragment(){
        withState{
            if(it.selectedFragment.peekContent() != STARTCHALENGE_RESULT)
                setState{copy(selectedFragment = Event(STARTCHALENGE_RESULT))}
        }
    }

    fun unsubscribe(){
        dispos?.dispose()
    }

    fun compare() {
        val questions = (state.value!!.getChalengeDetailles as Success).invoke().questions
        lastTime = questions.getOrNull((state.value!!.page.peekContent()+1))?.time ?: 0L
        val writeAnswer = writeAnswers.getOrElse(curentQuestion!!.id) { -1 }
        setState { copy(writeAnswer = Event(writeAnswer))}
    }
}
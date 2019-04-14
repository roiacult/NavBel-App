package com.roacult.kero.oxxy.domain.modules

sealed class ChallengeState{
    /**
     * this is when the request i send has error
     */
    object ConnexionProblem:ChallengeState()

    /**
     * i give you the hour and minute that left to the user to solve the challenge
     */
    data class ChallengeNotSolvedYet(val hour :Int , val Minute:Int )

    /**
     * this happen when he is trying to solve it but the challenge has been solved at this timeTakenPercentage
     */
    object ChallengeSolved
    /**
     * in the onComplete of the observable that when the timeTakenPercentage is over
     */
}
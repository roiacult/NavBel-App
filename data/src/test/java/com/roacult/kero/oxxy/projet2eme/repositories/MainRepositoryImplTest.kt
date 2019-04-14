package com.roacult.kero.oxxy.projet2eme.repositories


import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.roacult.kero.oxxy.domain.functional.Either
import com.roacult.kero.oxxy.projet2eme.local.MainLocal
import com.roacult.kero.oxxy.projet2eme.network.MainRemote
import com.roacult.kero.oxxy.projet2eme.network.entities.TrueOption
import com.roacult.kero.oxxy.projet2eme.network.entities.TrueOptions
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.mock

internal class MainRepositoryImplTest {
    lateinit var mockRemote :MainRemote
    lateinit var mockLocal :MainLocal
    lateinit var  trueOptons :List<TrueOption>
    lateinit var  options :Map<Long , Long >
     var  timePercent :Float = 0.0f

    @BeforeEach
    fun setUp() {
         mockRemote   =mock()
              trueOptons= listOf(TrueOption(15L , 20L, 15L),
                TrueOption(16L , 30L, 20L),
                TrueOption(18L, 35L, 10L)
            , TrueOption(19L, 36L, 15L),
                TrueOption(20L, 38L , 16L) , TrueOption(21L , 39L , 20L)
            )
        options = mapOf(Pair(15L , 20L), Pair(16L , 30L), Pair(18L , 35L), Pair(19L , 36L), Pair(20L, 38L), Pair(21L ,39L ))
        timePercent = 0.98f
        mockLocal = mock()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun correctChallenge() {
             val repo = MainRepositoryImpl(mockRemote , mockLocal)
            assertEquals(repo.correctChallenge(options , timePercent , trueOptons).points , 19)
    }
}
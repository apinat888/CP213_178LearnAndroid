package com.example.a178lablearnandroid.utils

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GameStatsCalculatorTest {

    private lateinit var calculator: GameStatsCalculator

    @Before
    fun setUp() {
        calculator = GameStatsCalculator()
    }

    @Test
    fun `increaseStat with normal amount should increase stat correctly`() {
        val currentStat = 10
        val amount = 1
        val result = calculator.increaseStat(currentStat, amount)
        assertEquals(11, result)
    }

    @Test
    fun `increaseStat exceeding max limit should be capped at MAX_STAT`() {
        val currentStat = 98
        val amount = 5
        val result = calculator.increaseStat(currentStat, amount)
        assertEquals(99, result)
    }

    @Test
    fun `decreaseStat with normal amount should decrease stat correctly`() {
        val currentStat = 10
        val amount = 1
        val result = calculator.decreaseStat(currentStat, amount)
        assertEquals(9, result)
    }

    @Test
    fun `decreaseStat below min limit should be capped at MIN_STAT`() {
        val currentStat = 2
        val amount = 5
        val result = calculator.decreaseStat(currentStat, amount)
        assertEquals(0, result)
    }
}

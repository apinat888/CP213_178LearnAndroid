package com.example.a178lablearnandroid.utils

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Local Unit Test สำหรับทดสอบ Business Logic ของ GameStatsCalculator
 * รันในเครื่อง (JVM) โดยไม่ต้องใช้ Emulator (อยู่ในโฟลเดอร์ test ไม่ใช่ androidTest)
 */
class GameStatsCalculatorTest {

    private lateinit var calculator: GameStatsCalculator

    @Before
    fun setUp() {
        // สร้าง Object ใหม่ก่อนที่จะเริ่มแต่ละ Test Case (Setup)
        calculator = GameStatsCalculator()
    }

    @Test
    fun `increaseStat with normal amount should increase stat correctly`() {
        // Arrange (เตรียมข้อมูล)
        val currentStat = 10
        val amount = 1

        // Act (ลงมือทดสอบ)
        val result = calculator.increaseStat(currentStat, amount)

        // Assert (ตรวจสอบผลลัพธ์)
        assertEquals(11, result)
    }

    @Test
    fun `increaseStat exceeding max limit should be capped at MAX_STAT`() {
        // Arrange
        val currentStat = 98
        val amount = 5

        // Act
        val result = calculator.increaseStat(currentStat, amount)

        // Assert
        // คาดหวังว่าจะไม่เกิน 99 (MAX_STAT)
        assertEquals(99, result)
    }

    @Test
    fun `decreaseStat with normal amount should decrease stat correctly`() {
        // Arrange
        val currentStat = 10
        val amount = 1

        // Act
        val result = calculator.decreaseStat(currentStat, amount)

        // Assert
        assertEquals(9, result)
    }

    @Test
    fun `decreaseStat below min limit should be capped at MIN_STAT`() {
        // Arrange
        val currentStat = 2
        val amount = 5 // ลดเยอะเกินจนติดลบ

        // Act
        val result = calculator.decreaseStat(currentStat, amount)

        // Assert
        // คาดหวังว่าจะไม่ต่ำกว่า 0 (MIN_STAT)
        assertEquals(0, result)
    }
}

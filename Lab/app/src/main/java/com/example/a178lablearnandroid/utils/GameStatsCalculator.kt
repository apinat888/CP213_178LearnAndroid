package com.example.a178lablearnandroid.utils

/**
 * GameStatsCalculator
 * คลาสนี้สำหรับจัดการ Business Logic ของค่าสถานะตัวละครในเกม
 * แยกออกมาจาก UI (MainActivity) เพื่อให้สามารถเขียน Unit Test ได้ง่าย (Local Unit Test)
 */
class GameStatsCalculator {

    companion object {
        const val MIN_STAT = 0
        const val MAX_STAT = 99
    }

    /**
     * เพิ่มค่าสถานะ
     * @param currentStat ค่าปัจจุบัน
     * @param amount จำนวนที่ต้องการเพิ่ม (ค่าเริ่มต้นคือ 1)
     * @return ค่าใหม่ที่ไม่เกิน MAX_STAT
     */
    fun increaseStat(currentStat: Int, amount: Int = 1): Int {
        val newStat = currentStat + amount
        return if (newStat > MAX_STAT) MAX_STAT else newStat
    }

    /**
     * ลดค่าสถานะ
     * @param currentStat ค่าปัจจุบัน
     * @param amount จำนวนที่ต้องการลด (ค่าเริ่มต้นคือ 1)
     * @return ค่าใหม่ที่ไม่ต่ำกว่า MIN_STAT
     */
    fun decreaseStat(currentStat: Int, amount: Int = 1): Int {
        val newStat = currentStat - amount
        return if (newStat < MIN_STAT) MIN_STAT else newStat
    }
}

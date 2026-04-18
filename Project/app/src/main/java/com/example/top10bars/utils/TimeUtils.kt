package com.example.top10bars.utils

import java.util.Calendar

enum class BarStatus {
    OPEN, CLOSED, CLOSING_SOON
}

fun getRealTimeStatus(openTime: String, closeTime: String): BarStatus {
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)
    val nowMins = currentHour * 60 + currentMinute
    
    val openParts = openTime.split(":")
    val closeParts = closeTime.split(":")
    if (openParts.size != 2 || closeParts.size != 2) return BarStatus.CLOSED
    
    val openMins = openParts[0].toInt() * 60 + openParts[1].toInt()
    var closeMins = closeParts[0].toInt() * 60 + closeParts[1].toInt()
    
    val isOvernight = closeMins <= openMins
    if (isOvernight) {
        closeMins += 24 * 60
    }
    
    var timeToCheck = nowMins
    if (isOvernight && nowMins < openMins && nowMins < (closeMins - 24 * 60)) {
        timeToCheck += 24 * 60
    }

    if (timeToCheck in openMins..<closeMins) {
        if (closeMins - timeToCheck <= 60) {
            return BarStatus.CLOSING_SOON
        }
        return BarStatus.OPEN
    }
    return BarStatus.CLOSED
}

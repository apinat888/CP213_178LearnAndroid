package com.example.top10bars.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.top10bars.data.local.FavoritesManager
import com.example.top10bars.data.local.NotificationManager
import com.example.top10bars.data.repository.BarRepository
import kotlinx.coroutines.flow.first
import java.util.*

class NotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val notificationManager = NotificationManager(applicationContext)
        val isEnabled = notificationManager.notificationsEnabled.first()
        
        if (!isEnabled) return Result.success()

        val favoritesManager = FavoritesManager(applicationContext)
        val favoriteIds = favoritesManager.favoritesIds.first()
        
        if (favoriteIds.isEmpty()) return Result.success()

        val repository = BarRepository()
        val allBars = repository.getBars()
        val favoriteBars = allBars.filter { favoriteIds.contains(it.id) }

        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        val currentTimeInMinutes = currentHour * 60 + currentMinute

        val notificationHelper = NotificationHelper(applicationContext)

        favoriteBars.forEach { bar ->
            val timeParts = bar.openTime.split(":")
            if (timeParts.size == 2) {
                val openHour = timeParts[0].toIntOrNull() ?: 0
                val openMinute = timeParts[1].toIntOrNull() ?: 0
                val openTimeInMinutes = openHour * 60 + openMinute

                // แจ้งเตือนถ้าเวลาปัจจุบันตรงกับเวลาเปิด หรือเลยมาไม่เกิน 15 นาที
                if (currentTimeInMinutes >= openTimeInMinutes && currentTimeInMinutes < openTimeInMinutes + 15) {
                    notificationHelper.sendNotification(
                        title = "ร้านโปรดเปิดแล้ว!",
                        message = "คืนนี้ไปเจอกันที่ ${bar.name} ไหม? ร้านเปิดให้บริการแล้วนะ"
                    )
                }
            }
        }

        return Result.success()
    }
}

package com.example.top10bars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.top10bars.data.local.ThemeManager
import com.example.top10bars.ui.navigation.AppNavigation
import com.example.top10bars.ui.theme.Top10BarsTheme
import com.example.top10bars.utils.NotificationWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // เริ่มต้นการทำงานเบื้องหลังสำหรับแจ้งเตือน
        setupNotificationWork()

        setContent {
            val themeManager = remember { ThemeManager(applicationContext) }
            val isDarkTheme by themeManager.isDarkTheme.collectAsState()

            Top10BarsTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(themeManager)
                }
            }
        }
    }

    private fun setupNotificationWork() {
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
            .build()
        
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "BarOpeningNotification",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}

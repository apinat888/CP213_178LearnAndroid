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
import com.example.top10bars.data.local.ThemeManager
import com.example.top10bars.ui.navigation.AppNavigation
import com.example.top10bars.ui.theme.Top10BarsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // สร้าง ThemeManager เพื่อจัดการโหมดมืด/สว่าง
            val themeManager = remember { ThemeManager(applicationContext) }
            val isDarkTheme by themeManager.isDarkTheme.collectAsState()

            Top10BarsTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // ส่ง themeManager เข้าไปในระบบ Navigation
                    AppNavigation(themeManager)
                }
            }
        }
    }
}

package com.example.top10bars.data.local

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NotificationManager(context: Context) {
    private val prefs = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
    
    private val _notificationsEnabled = MutableStateFlow(prefs.getBoolean("enabled", true))
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled

    fun toggleNotifications() {
        val newValue = !_notificationsEnabled.value
        _notificationsEnabled.value = newValue
        prefs.edit().putBoolean("enabled", newValue).apply()
    }
}

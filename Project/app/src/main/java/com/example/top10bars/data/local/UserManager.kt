package com.example.top10bars.data.local

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class UserProfile(
    val name: String,
    val email: String
)

class UserManager(context: Context) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    
    private val _userProfile = MutableStateFlow(
        UserProfile(
            name = prefs.getString("user_name", "Guest User") ?: "Guest User",
            email = prefs.getString("user_email", "guest@nightpick.com") ?: "guest@nightpick.com"
        )
    )
    val userProfile: StateFlow<UserProfile> = _userProfile

    fun updateProfile(name: String, email: String) {
        _userProfile.value = UserProfile(name, email)
        prefs.edit().apply {
            putString("user_name", name)
            putString("user_email", email)
            apply()
        }
    }
}

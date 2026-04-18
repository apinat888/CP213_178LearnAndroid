package com.example.top10bars.data.local

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoritesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("top10bars_prefs", Context.MODE_PRIVATE)
    
    private val _favoritesIds = MutableStateFlow<Set<String>>(emptySet())
    val favoritesIds: StateFlow<Set<String>> = _favoritesIds.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        val savedIds = prefs.getStringSet("favorites", emptySet()) ?: emptySet()
        _favoritesIds.value = savedIds
    }

    fun toggleFavorite(barId: String) {
        val current = _favoritesIds.value.toMutableSet()
        if (current.contains(barId)) {
            current.remove(barId)
        } else {
            current.add(barId)
        }
        _favoritesIds.value = current
        prefs.edit().putStringSet("favorites", current).apply()
    }

    fun isFavorite(barId: String): Boolean {
        return _favoritesIds.value.contains(barId)
    }
}

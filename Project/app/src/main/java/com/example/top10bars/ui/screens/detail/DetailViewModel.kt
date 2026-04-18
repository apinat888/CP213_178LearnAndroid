package com.example.top10bars.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.top10bars.data.local.FavoritesManager
import com.example.top10bars.data.model.Bar
import com.example.top10bars.data.repository.BarRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel(
    private val barId: String,
    private val repository: BarRepository,
    private val favoritesManager: FavoritesManager
) : ViewModel() {

    private val _bar = MutableStateFlow<Bar?>(null)
    val bar: StateFlow<Bar?> = _bar

    val isFavorite = favoritesManager.favoritesIds.map { it.contains(barId) }
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {
        viewModelScope.launch {
            _bar.value = repository.getBars().find { it.id == barId }
        }
    }

    fun toggleFavorite() {
        favoritesManager.toggleFavorite(barId)
    }
}

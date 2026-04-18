package com.example.top10bars.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.top10bars.data.local.FavoritesManager
import com.example.top10bars.data.model.Bar
import com.example.top10bars.data.repository.BarRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: BarRepository,
    private val favoritesManager: FavoritesManager
) : ViewModel() {

    private val _bars = MutableStateFlow<List<Bar>>(emptyList())
    val favoritesIds = favoritesManager.favoritesIds

    val uiState = combine(_bars, favoritesIds) { bars, faves ->
        bars.filter { faves.contains(it.id) }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        viewModelScope.launch {
            _bars.value = repository.getBars()
        }
    }

    fun toggleFavorite(barId: String) {
        favoritesManager.toggleFavorite(barId)
    }
}

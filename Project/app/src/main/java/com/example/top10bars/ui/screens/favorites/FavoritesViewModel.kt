package com.example.top10bars.ui.screens.favorites

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.top10bars.data.local.FavoritesManager
import com.example.top10bars.data.model.Bar
import com.example.top10bars.data.repository.BarRepository
import com.example.top10bars.utils.calculateDistance
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: BarRepository,
    private val favoritesManager: FavoritesManager
) : ViewModel() {

    private val _bars = MutableStateFlow<List<Bar>>(emptyList())
    private val _currentLocation = MutableStateFlow<Location?>(null)
    val favoritesIds = favoritesManager.favoritesIds

    val uiState = combine(_bars, favoritesIds, _currentLocation) { bars, faves, location ->
        bars.filter { faves.contains(it.id) }.map { bar ->
            val dist = if (location != null) {
                calculateDistance(location.latitude, location.longitude, bar.locationLat, bar.locationLng)
            } else null
            Pair(bar, dist)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        viewModelScope.launch {
            _bars.value = repository.getBars()
        }
    }

    fun updateLocation(location: Location) {
        _currentLocation.value = location
    }

    fun toggleFavorite(barId: String) {
        favoritesManager.toggleFavorite(barId)
    }
}

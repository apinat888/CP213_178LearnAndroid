package com.example.top10bars.ui.screens.home

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.top10bars.data.local.FavoritesManager
import com.example.top10bars.data.model.Bar
import com.example.top10bars.data.repository.BarRepository
import com.example.top10bars.utils.calculateDistance
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: BarRepository,
    private val favoritesManager: FavoritesManager
) : ViewModel() {

    private val _bars = MutableStateFlow<List<Bar>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    private val _selectedType = MutableStateFlow<String?>(null)
    private val _currentLocation = MutableStateFlow<Location?>(null)

    val favorites = favoritesManager.favoritesIds

    val uiState = combine(_bars, _searchQuery, _selectedType, _currentLocation) { bars, query, type, location ->
        var filtered = bars
        if (query.isNotBlank()) {
            filtered = filtered.filter { it.name.contains(query, ignoreCase = true) }
        }
        if (type != null) {
            filtered = filtered.filter { it.type == type }
        }
        
        // Compute distances and sort
        val withDistances = filtered.map { bar ->
            val dist = if (location != null) {
                calculateDistance(location.latitude, location.longitude, bar.locationLat, bar.locationLng)
            } else null
            Pair(bar, dist)
        }
        
        withDistances.sortedBy { it.second ?: Float.MAX_VALUE }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        viewModelScope.launch {
            _bars.value = repository.getBars()
        }
    }

    fun getRandomBar(): Bar? {
        return _bars.value.randomOrNull()
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onTypeSelected(type: String?) {
        _selectedType.value = type
    }
    
    fun updateLocation(location: Location) {
        _currentLocation.value = location
    }

    fun toggleFavorite(barId: String) {
        favoritesManager.toggleFavorite(barId)
    }
}

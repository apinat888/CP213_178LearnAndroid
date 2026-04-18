package com.example.top10bars.ui.screens.home

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.top10bars.ui.components.BarCard
import com.example.top10bars.ui.theme.*
import com.example.top10bars.utils.LocationHelper
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    hasLocationPermission: Boolean,
    onNavigateToDetail: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var selectedType by remember { mutableStateOf<String?>(null) }
    
    val context = LocalContext.current

    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            val locationHelper = LocationHelper(context)
            locationHelper.getLocationUpdates().collectLatest { location ->
                viewModel.updateLocation(location)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { 
                searchQuery = it
                viewModel.onSearchQueryChanged(it.text)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("ค้นหาชื่อร้าน...", color = TextSecondary) },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search", tint = TextSecondary) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = DarkSurface,
                unfocusedContainerColor = DarkSurface,
                focusedBorderColor = GoldAccent,
                unfocusedBorderColor = DarkSurface,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Filters
        val types = listOf("Rooftop", "Jazz", "Cocktail", "Club", "Speakeasy", "Lounge")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                FilterChip(
                    selected = selectedType == null,
                    onClick = { 
                        selectedType = null
                        viewModel.onTypeSelected(null)
                    },
                    label = { Text("All") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PurpleAccent,
                        selectedLabelColor = TextPrimary
                    )
                )
            }
            items(types) { type ->
                FilterChip(
                    selected = selectedType == type,
                    onClick = { 
                        selectedType = type
                        viewModel.onTypeSelected(type)
                    },
                    label = { Text(type) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PurpleAccent,
                        selectedLabelColor = TextPrimary
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        // List
        LazyColumn {
            items(uiState, key = { it.first.id }) { (bar, distance) ->
                BarCard(
                    bar = bar,
                    distanceKm = distance,
                    isFavorite = favorites.contains(bar.id),
                    onFavoriteClick = { viewModel.toggleFavorite(bar.id) },
                    onClick = { onNavigateToDetail(bar.id) }
                )
            }
        }
    }
}

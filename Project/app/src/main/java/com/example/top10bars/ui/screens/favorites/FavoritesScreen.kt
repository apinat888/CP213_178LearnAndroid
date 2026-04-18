package com.example.top10bars.ui.screens.favorites

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.top10bars.ui.components.BarCard
import com.example.top10bars.ui.theme.DarkBackground
import com.example.top10bars.ui.theme.TextSecondary
import com.example.top10bars.utils.LocationHelper
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("MissingPermission")
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    hasLocationPermission: Boolean,
    onNavigateToDetail: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val favoritesIds by viewModel.favoritesIds.collectAsStateWithLifecycle()
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
        Text(
            text = "ร้านโปรดของคุณ",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (uiState.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "ยังไม่มีร้านโปรด\nกดหัวใจเพื่อบันทึกร้านที่ชอบ",
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn {
                items(uiState, key = { it.first.id }) { (bar, distance) ->
                    BarCard(
                        bar = bar,
                        distanceKm = distance,
                        isFavorite = favoritesIds.contains(bar.id),
                        onFavoriteClick = { viewModel.toggleFavorite(bar.id) },
                        onClick = { onNavigateToDetail(bar.id) }
                    )
                }
            }
        }
    }
}

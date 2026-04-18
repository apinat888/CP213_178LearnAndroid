package com.example.top10bars.ui.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.top10bars.ui.components.BarCard
import com.example.top10bars.ui.theme.DarkBackground
import com.example.top10bars.ui.theme.TextSecondary

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onNavigateToDetail: (String) -> Unit
) {
    val favoriteBars by viewModel.uiState.collectAsStateWithLifecycle()
    val favoritesIds by viewModel.favoritesIds.collectAsStateWithLifecycle()

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

        if (favoriteBars.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "ยังไม่มีร้านโปรด\nกดหัวใจเพื่อบันทึกร้านที่ชอบ",
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn {
                items(favoriteBars, key = { it.id }) { bar ->
                    BarCard(
                        bar = bar,
                        distanceKm = null,
                        isFavorite = favoritesIds.contains(bar.id),
                        onFavoriteClick = { viewModel.toggleFavorite(bar.id) },
                        onClick = { onNavigateToDetail(bar.id) }
                    )
                }
            }
        }
    }
}

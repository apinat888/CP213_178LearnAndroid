package com.example.top10bars.ui.screens.map

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.top10bars.data.model.Bar
import com.example.top10bars.ui.theme.DarkBackground
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@SuppressLint("MissingPermission")
@Composable
fun MapScreen(
    bars: List<Bar>,
    hasLocationPermission: Boolean,
    onNavigateToDetail: (String) -> Unit
) {
    val bangkok = LatLng(13.7563, 100.5018)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(bangkok, 11f)
    }

    Box(modifier = Modifier.fillMaxSize().background(DarkBackground)) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
            uiSettings = MapUiSettings(myLocationButtonEnabled = true)
        ) {
            bars.forEach { bar ->
                Marker(
                    state = MarkerState(position = LatLng(bar.locationLat, bar.locationLng)),
                    title = bar.name,
                    snippet = "${bar.type} - Rating: ${bar.rating}",
                    onInfoWindowClick = { onNavigateToDetail(bar.id) }
                )
            }
        }
    }
}

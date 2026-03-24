package com.example.a178lablearnandroid

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                SensorScreen(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

@Composable
fun LifecycleDemo(modifier: Modifier = Modifier) {
    var show by remember { mutableStateOf(true) }

    Column(modifier = modifier) {
        Button(onClick = { show = !show }) {
            Text(if (show) "Hide" else "Show")
        }

        if (show) {
            LifecycleComponent()
        }
    }
}

@Composable
fun LifecycleComponent() {
    // State สำหรับ Recomposition
    var text by remember { mutableStateOf("") }

    // Log เมื่อ Recompose
    SideEffect {
        Log.d("ComposeLifecycle", "Recompose: $text")
    }

    // Log เมื่อ Enter/Leave
    DisposableEffect(Unit) {
        Log.d("ComposeLifecycle", "Enter Composition")
        onDispose {
            Log.d("ComposeLifecycle", "Leave Composition")
        }
    }

    Column {
        Text(text = "Unstable State: $text")
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Type to Recompose") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    LifecycleDemo()
}

@Composable
fun SensorScreen(modifier: Modifier = Modifier, viewModel: SensorViewModel = viewModel()) {
    val accel by viewModel.accelerometerData.collectAsState()
    val location by viewModel.locationData.collectAsState()
    val context = LocalContext.current
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                              permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
    }

    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            viewModel.startLocationUpdates()
        } else {
            permissionLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Accelerometer Data", style = MaterialTheme.typography.headlineSmall)
        Text(text = "X: ${accel[0]}")
        Text(text = "Y: ${accel[1]}")
        Text(text = "Z: ${accel[2]}")
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(text = "Location Data", style = MaterialTheme.typography.headlineSmall)
        if (hasLocationPermission) {
            if (location != null) {
                Text(text = "Lat: ${location?.latitude}")
                Text(text = "Lng: ${location?.longitude}")
            } else {
                Text(text = "Waiting for location...")
            }
        } else {
            Text(text = "Location permission denied.")
            Button(onClick = {
                permissionLauncher.launch(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                )
            }) {
                Text("Request Permission")
            }
        }
    }
}
package com.example.a178lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a178lablearnandroid.ui.theme._178LabLearnAndroidTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

// 1. ViewModel จัดการ Error Event ด้วย SharedFlow (One-time event)
class ErrorViewModel : ViewModel() {
    // ใช้ SharedFlow สำหรับเหตุการณ์ที่เกิดขึ้นครั้งเดียว (เช่น Snackbar, Navigation)
    private val _errorEvents = MutableSharedFlow<String>()
    val errorEvents = _errorEvents.asSharedFlow()

    fun triggerError() {
        viewModelScope.launch {
            // จำลองการเกิด Error
            _errorEvents.emit("เกิดข้อผิดพลาดในการเชื่อมต่อเซิร์ฟเวอร์!")
        }
    }
}

class Part5Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _178LabLearnAndroidTheme {
                // 2. เตรียม SnackbarHostState
                val snackbarHostState = remember { SnackbarHostState() }
                
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    ErrorScreen(
                        modifier = Modifier.padding(innerPadding),
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: ErrorViewModel = viewModel()
) {
    // 3. ใช้ LaunchedEffect เพื่อ Observe ข้อมูลจาก SharedFlow
    // เมื่อมี Error หลุดออกมาจาก Flow, LaunchedEffect จะทำงาน
    LaunchedEffect(Unit) {
        viewModel.errorEvents.collect { message ->
            // สั่งโชว์ Snackbar เมื่อได้รับ Event
            snackbarHostState.showSnackbar(
                message = message,
                withDismissAction = true
            )
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 4. ปุ่มสำหรับ Trigger Error
        Button(onClick = { viewModel.triggerError() }) {
            Text("Trigger Error (Show Snackbar)")
        }
    }
}

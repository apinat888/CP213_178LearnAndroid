package com.example.a178lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a178lablearnandroid.ui.theme._178LabLearnAndroidTheme

class Part8Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _178LabLearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ResponsiveProfileScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ResponsiveProfileScreen(modifier: Modifier = Modifier) {
    // 1. ใช้ BoxWithConstraints เพื่อตรวจสอบขนาดพื้นที่ที่มีอยู่
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // ดึงค่าความกว้างสูงสุดมาเช็ค
        val isTablet = maxWidth >= 600.dp

        if (isTablet) {
            // 2. แนวนอน (Tablet/Landscape): แสดงเป็น Row
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileImage(modifier = Modifier.size(200.dp))
                Spacer(modifier = Modifier.width(32.dp))
                ProfileInfo(
                    modifier = Modifier.weight(1f),
                    isTablet = true
                )
            }
        } else {
            // 3. แนวตั้ง (Mobile/Portrait): แสดงเป็น Column
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                ProfileImage(modifier = Modifier.size(150.dp))
                Spacer(modifier = Modifier.height(24.dp))
                ProfileInfo(
                    modifier = Modifier.fillMaxWidth(),
                    isTablet = false
                )
            }
        }
    }
}

@Composable
fun ProfileImage(modifier: Modifier = Modifier) {
    // กล่องสมมติแทนรูปโปรไฟล์
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Profile", color = Color.Gray)
    }
}

@Composable
fun ProfileInfo(modifier: Modifier = Modifier, isTablet: Boolean) {
    Column(modifier = modifier) {
        Text(
            text = "John Doe",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Android Developer | UI Designer",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "นี่คือตัวอย่างการทำ Responsive Design ใน Jetpack Compose โดยใช้ BoxWithConstraints " +
                    "ถ้าจอแคบ (มือถือ) ข้อมูลจะเรียงลงด้านล่าง แต่ถ้าจอกว้าง (แท็บเล็ต) ข้อมูลจะมาอยู่ข้างรูปโปรไฟล์ " +
                    "ลองหมุนหน้าจอเครื่องจำลองดูเพื่อเห็นความเปลี่ยนแปลงได้ทันที!",
            lineHeight = 22.sp
        )
    }
}

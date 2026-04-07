package com.example.a178lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a178lablearnandroid.ui.theme._178LabLearnAndroidTheme

class Part10Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _178LabLearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppWidgetStudyScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppWidgetStudyScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Part 10: Jetpack Glance & App Widgets",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- ส่วนอธิบาย Concept ---
        Text(
            text = "Concept: Jetpack Glance (Weather Widget)",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text(
                text = "การทำ App Widget ด้วย Jetpack Glance ช่วยให้เราเขียน UI ของวิดเจ็ตด้วยรูปแบบที่คล้ายกับ Compose มาก " +
                        "แต่มันจะถูกแปลงเป็น RemoteViews เพื่อไปแสดงผลบนหน้าจอ Home Screen ของ Android",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- ส่วนจำลอง UI ของ Widget (ตามรูปตัวอย่าง) ---
        Text(
            text = "จำลองหน้าตา Widget (Glance Simulation):",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // จำลองวิดเจ็ตสภาพอากาศ
        Box(
            modifier = Modifier
                .size(280.dp, 200.dp)
                .background(Color(0xFF00A99D), RoundedCornerShape(24.dp)) // สี Teal ตามรูป
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // ส่วนบน: ชื่อเมืองและสถานะ
                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = "กรุงเทพมหา\nนคร",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // แก้ไข: เปลี่ยนจาก Cloud เป็น Info เพราะ Cloud ต้องใช้ library เสริม
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "ท้องฟ้าแจ่มใส (Glance)",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // ส่วนล่าง: อุณหภูมิและปุ่ม
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "32°C",
                        color = Color.White,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = { /* จำลองการอัปเดต */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A5D8A)), // สีปุ่มเข้ม
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(40.dp)
                    ) {
                        Text("อัปเดตข้อมูล", fontSize = 12.sp, color = Color.White)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- คำอธิบายเพิ่มเติม ---
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "จุดเด่นของ Jetpack Glance ที่เห็นจากตัวอย่าง:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        val glancePoints = listOf(
            "1. Simplified UI: ใช้โครงสร้าง Row/Column เหมือน Compose ทำให้จัดวาง Layout ที่ซับซ้อนได้ง่าย",
            "2. Interactive: สามารถใส่ปุ่ม (Button) เพื่อรับการคลิกและสั่งงานผ่าน Action API ได้ทันที",
            "3. Native Look: ให้หน้าตาที่ทันสมัยและปรับแต่ง Corner Radius ได้ง่ายเหมือน Card ปกติ",
            "4. Remote Views: แม้จะเขียนเหมือน Compose แต่ Glance จะจัดการเรื่องการส่ง UI ข้าม Process ไปที่ Home Screen ให้โดยอัตโนมัติ"
        )

        glancePoints.forEach { point ->
            Text(
                text = point,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "หมายเหตุ: การรัน Widget นี้จริงๆ บนเครื่อง ต้องสร้างคลาสที่สืบทอดจาก GlanceAppWidget และประกาศใน Manifest (ซึ่งต้องใช้ Dependency เพิ่มเติม)",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

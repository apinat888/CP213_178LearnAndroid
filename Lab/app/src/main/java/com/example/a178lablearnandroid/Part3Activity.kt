package com.example.a178lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.a178lablearnandroid.ui.theme._178LabLearnAndroidTheme

class Part3Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _178LabLearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        // ตัวอย่างการเรียกใช้งาน DonutChart ด้วยข้อมูลสัดส่วนและสี
                        DonutChart(
                            proportions = listOf(30f, 40f, 30f),
                            colors = listOf(
                                Color(0xFF6200EE), // Purple
                                Color(0xFF03DAC6), // Teal
                                Color(0xFFFF0266)  // Pink
                            ),
                            modifier = Modifier.size(300.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Composable สำหรับวาด Donut Chart ด้วย Canvas และ Animation
 * @param proportions รายการของเปอร์เซ็นต์ (รวมกันควรได้ 100)
 * @param colors รายการสีที่ใช้ในแต่ละส่วน
 */
@Composable
fun DonutChart(
    proportions: List<Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    // 1. สร้าง State สำหรับ Animation (0f ถึง 1f)
    val animationProgress = remember { Animatable(0f) }

    // 2. Trigger Animation เมื่อ Composable ถูกแสดงผล (LaunchedEffect)
    LaunchedEffect(Unit) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1500) // ระยะเวลา 1.5 วินาที
        )
    }

    Canvas(
        modifier = modifier
            .aspectRatio(1f) // บังคับให้เป็นสี่เหลี่ยมจัตุรัส
            .padding(32.dp)
    ) {
        val strokeWidth = 80f // ความหนาของเส้นโดนัท
        var startAngle = -90f // เริ่มวาดที่ตำแหน่ง 12 นาฬิกา

        // วนลูปวาดแต่ละส่วนของกราฟ
        proportions.forEachIndexed { index, proportion ->
            // คำนวณมุมกวาด (Sweep Angle) ของส่วนนี้
            // คูณด้วย animationProgress เพื่อให้เกิดเอฟเฟกต์ค่อยๆ วาด
            val sweepAngle = (proportion / 100f) * 360f * animationProgress.value
            
            val color = colors.getOrElse(index) { Color.Gray }

            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false, // ไม่ลากเส้นไปที่จุดศูนย์กลาง (เพื่อให้เป็นโดนัท)
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Butt // ปลายเส้นแบบตัดตรง (เลือก Round ได้ถ้าต้องการความมน)
                )
            )

            // อัปเดตมุมเริ่มสำหรับชิ้นส่วนถัดไป
            startAngle += (proportion / 100f) * 360f
        }
    }
}

package com.example.a178lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class Part1Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LikeButtonDemo()
                }
            }
        }
    }
}

@Composable
fun LikeButtonDemo() {
    // 1. State สำหรับเก็บสถานะการ Like (โจทย์กำหนดให้ใช้ remember เก็บสถานะ ไม่ต้องใช้ ViewModel)
    var isLiked by remember { mutableStateOf(false) }

    // State สำหรับเช็คว่าปุ่มกำลังถูกกดอยู่หรือไม่ (เพื่อทำ Scale Animation ตอนที่กดค้าง)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // 2. Animate ขนาดของปุ่ม (Scale) 
    // เมื่อกดปุ่ม ขนาดจะขยายขึ้นเป็น 1.1 เท่าตัว พอปล่อยจะเด้งกลับด้วย spring animation
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scaleAnimation"
    )

    // 3. Animate สีพื้นหลังของปุ่ม (จากเทา เป็น ชมพู)
    val buttonColor by animateColorAsState(
        targetValue = if (isLiked) Color(0xFFE91E63) else Color.LightGray,
        label = "colorAnimation"
    )

    // ปรับสีข้อความและไอคอนให้ตัดกับสีพื้นหลัง
    val contentColor = if (isLiked) Color.White else Color.Black

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { isLiked = !isLiked },
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = contentColor
            ),
            modifier = Modifier.scale(scale)
        ) {
            // 4. AnimatedVisibility สำหรับการแสดงผล Icon หัวใจโผล่ขึ้นมาข้างๆ ข้อความ
            AnimatedVisibility(visible = isLiked) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Heart Icon",
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            Text(text = if (isLiked) "Liked" else "Like")
        }
    }
}

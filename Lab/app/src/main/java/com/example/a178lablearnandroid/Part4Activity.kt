package com.example.a178lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a178lablearnandroid.ui.theme._178LabLearnAndroidTheme

// 1. ViewModel จัดการ State ของ To-Do List
class TodoViewModel : ViewModel() {
    // ใช้ mutableStateListOf เพื่อให้ Compose ตรวจจับการเพิ่ม/ลบได้อัตโนมัติ
    private val _todoItems = mutableStateListOf(
        "เรียนเขียน Android ด้วย Compose",
        "ส่งงาน Lab Part 4",
        "ไปซื้อของที่ตลาด",
        "ออกกำลังกาย 30 นาที",
        "อ่านหนังสือ Clean Code"
    )
    val todoItems: List<String> = _todoItems

    fun removeItem(item: String) {
        _todoItems.remove(item)
    }
}

class Part4Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _178LabLearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TodoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TodoScreen(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = viewModel()
) {
    val items = viewModel.todoItems

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        items(
            items = items,
            key = { it } // สำคัญมากสำหรับ Animation และ Swipe: ต้องมี key ที่ไม่ซ้ำกัน
        ) { item ->
            SwipeToDeleteItem(
                item = item,
                onDismiss = { viewModel.removeItem(item) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteItem(
    item: String,
    onDismiss: () -> Unit
) {
    // สถานะการปัด
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDismiss()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false, // ปิดการปัดจากซ้ายไปขวา (Start to End)
        backgroundContent = {
            // พื้นหลังเมื่อมีการปัด (ในที่นี้คือฝั่งขวาที่เป็นสีแดงและถังขยะ)
            val color = when (dismissState.dismissDirection) {
                SwipeToDismissBoxValue.EndToStart -> Color.Red
                else -> Color.Transparent
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color, MaterialTheme.shapes.medium)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Icon",
                    tint = Color.White
                )
            }
        },
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        // เนื้อหาหลักของ Item
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Text(
                text = item,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

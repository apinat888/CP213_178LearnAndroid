package com.example.a178lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a178lablearnandroid.ui.theme._178LabLearnAndroidTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 1. สร้าง ViewModel จัดการ State
class ContactViewModel : ViewModel() {
    // State ถือรายชื่อ (Mock ข้อมูล)
    private val _contacts = mutableStateListOf<String>()
    val contacts: List<String> = _contacts

    // State จัดการสถานะการโหลด
    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading.value

    // ตัวแปรสำหรับจำลองการรันอักษร A-Z
    private var currentLetterChar = 'A'
    private var hasMoreData = true

    init {
        // โหลดข้อมูลชุดแรกเมื่อเริ่มต้น
        loadMoreData()
    }

    fun loadMoreData() {
        // ถ้ายุ่งอยู่ หรือ ข้อมูลหมดแล้ว (ถึง Z แล้ว) ให้ข้ามไป
        if (_isLoading.value || !hasMoreData) return

        viewModelScope.launch {
            _isLoading.value = true

            // จำลองการโหลดจาก Server 2 วินาที
            delay(2000)

            val newBatch = mutableListOf<String>()
            // โหลดทีละ 3 ตัวอักษร
            val endLetter = (currentLetterChar + 2).coerceAtMost('Z')

            if (currentLetterChar <= 'Z') {
                for (c in currentLetterChar..endLetter) {
                    for (i in 1..10) { // สร้างรายชื่อสมมติ 10 คนต่อตัวอักษร
                        newBatch.add("$c - Mock Contact Name $i")
                    }
                }
                _contacts.addAll(newBatch)
                currentLetterChar = (endLetter.code + 1).toChar()
            } else {
                hasMoreData = false // โหลดครบถึง Z แล้ว
            }

            _isLoading.value = false
        }
    }
}

class Part2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _178LabLearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // เรียกใช้งานหน้า ContactScreen
                    ContactScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// 2. สร้าง Composable UI
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactScreen(
    modifier: Modifier = Modifier,
    viewModel: ContactViewModel = viewModel()
) {
    val contacts = viewModel.contacts
    val isLoading = viewModel.isLoading
    val listState = rememberLazyListState()

    // จัดกลุ่มข้อมูลตามตัวอักษรตัวแรก (A, B, C, ...) สำหรับนำไปทำ Sticky Header
    val groupedContacts = contacts.groupBy { it.first().uppercaseChar() }

    // สังเกตการ Scroll ว่าถึงด้านล่างสุดของ List หรือยัง (Pagination Trigger)
    val isAtBottom by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (layoutInfo.totalItemsCount == 0) {
                false
            } else {
                val lastVisibleItem = visibleItemsInfo.lastOrNull()
                // เช็คว่าไอเทมที่กำลังโชว์อยู่ เป็นไอเทมสุดท้ายในลิสต์หรือไม่
                lastVisibleItem?.index == layoutInfo.totalItemsCount - 1
            }
        }
    }

    // 3. Trigger ViewModel เพื่อจำลองการโหลดข้อมูลเพิ่ม
    LaunchedEffect(isAtBottom) {
        if (isAtBottom && !isLoading) {
            viewModel.loadMoreData()
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize()
    ) {
        // วนลูปข้อมูลที่ถูก Group ไว้เพื่อสร้าง Sticky Header และ Items
        groupedContacts.forEach { (initial, contactsForInitial) ->

            // ใช้ stickyHeader สำหรับตัวอักษรนำหน้า
            stickyHeader {
                Text(
                    text = initial.toString(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // แสดงรายชื่อคนในหมวดหมู่นั้นๆ
            items(contactsForInitial) { contact ->
                Text(
                    text = contact,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            }
        }

        // 4. แสดง CircularProgressIndicator ระหว่างรอโหลดข้อมูลด้านล่างสุด
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

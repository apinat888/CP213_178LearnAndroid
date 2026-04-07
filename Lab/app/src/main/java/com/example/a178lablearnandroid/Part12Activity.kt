package com.example.a178lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.a178lablearnandroid.ui.theme._178LabLearnAndroidTheme
import kotlinx.coroutines.launch

class Part12Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _178LabLearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BottomSheetAndDialogScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetAndDialogScreen(modifier: Modifier = Modifier) {
    var showSheet by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Part 12: Bottom Sheet & Dialog",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- อธิบาย Concept ---
        Text(
            text = "Concept Study:",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        Text(
            text = "1. Modal Bottom Sheet: ใช้สำหรับแสดงเนื้อหาเพิ่มเติมหรือตัวเลือกที่เกี่ยวข้องกับบริบทปัจจุบัน โดยจะเลื่อนขึ้นมาจากขอบจอด้านล่าง มักใช้กับรายการเมนูสั้นๆ หรือการตั้งค่าด่วน\n\n" +
                   "2. Middle Dialog (AlertDialog): ใช้สำหรับขัดจังหวะผู้ใช้เพื่อแจ้งข้อมูลสำคัญหรือขอการยืนยันก่อนดำเนินการ (เช่น การลบข้อมูล) จะแสดงผลกึ่งกลางหน้าจอและบล็อกการทำงานอื่นจนกว่าจะตอบสนอง",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ปุ่มทดสอบ Bottom Sheet
        Button(
            onClick = { showSheet = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show Modal Bottom Sheet")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ปุ่มทดสอบ Dialog
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show Middle Dialog (AlertDialog)")
        }

        // --- Modal Bottom Sheet Implementation ---
        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState
            ) {
                // เนื้อหาภายใน Bottom Sheet
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("นี่คือ Modal Bottom Sheet", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("คุณสามารถใส่เมนูหรือข้อมูลเพิ่มเติมตรงนี้ได้")
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showSheet = false
                            }
                        }
                    }) {
                        Text("ปิด Bottom Sheet")
                    }
                }
            }
        }

        // --- AlertDialog Implementation ---
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("ยืนยันการดำเนินการ") },
                text = { Text("นี่คือ Middle Dialog (AlertDialog) ใช้สำหรับแจ้งเตือนหรือขอการยืนยันที่สำคัญ") },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("ตกลง")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("ยกเลิก")
                    }
                }
            )
        }
    }
}

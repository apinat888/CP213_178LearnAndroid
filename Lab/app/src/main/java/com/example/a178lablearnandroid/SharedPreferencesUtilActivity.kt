package com.example.a178lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a178lablearnandroid.ui.theme._178LabLearnAndroidTheme
import com.example.a178lablearnandroid.utils.SharedPreferencesUtil
import com.example.a178lablearnandroid.ui.theme._178LabLearnAndroidTheme

class SharedPreferencesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. เชื่อมต่อเครื่องมือเข้ากับ Context ของแอพ
        SharedPreferencesUtil.init(this)

        // การบันทึกค่า (เช่น เมื่อกดปุ่ม Save)
        SharedPreferencesUtil.saveString("user_name", "Naratip")
        SharedPreferencesUtil.saveBoolean("is_dark_mode", true)

        // การดึงค่ามาใช้งาน (เช่น เมื่อเปิดแอพขึ้นมาใหม่)
        val name = SharedPreferencesUtil.getString("user_name")
        val darkMode = SharedPreferencesUtil.getBoolean("is_dark_mode")

        enableEdgeToEdge()
        setContent {
            _178LabLearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "user_name",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _178LabLearnAndroidTheme {
        Greeting("Android")
    }
}
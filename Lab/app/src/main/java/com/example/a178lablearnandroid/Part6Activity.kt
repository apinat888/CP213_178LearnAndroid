package com.example.a178lablearnandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a178lablearnandroid.ui.theme._178LabLearnAndroidTheme

// 1. ViewModel จัดการ State ของ URL
class WebViewModel : ViewModel() {
    var url by mutableStateOf("https://www.google.com")
        private set

    fun updateUrl(newUrl: String) {
        url = if (newUrl.startsWith("http")) newUrl else "https://$newUrl"
    }
}

class Part6Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _178LabLearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WebScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun WebScreen(
    modifier: Modifier = Modifier,
    viewModel: WebViewModel = viewModel()
) {
    // แก้ไข: ต้องครอบด้วย remember เพื่อไม่ให้ค่าหายตอน Recomposition
    var textInput by remember { mutableStateOf(viewModel.url) }

    Column(modifier = modifier.fillMaxSize()) {
        // 2. ส่วนกรอก URL (TextField + Button)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                modifier = Modifier.weight(1f),
                label = { Text("Enter URL") },
                singleLine = true
            )
            Button(
                onClick = { viewModel.updateUrl(textInput) },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Go")
            }
        }

        // 3. AndroidView สำหรับฝัง WebView (Inflate จาก XML)
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                // โหลด WebView จาก res/layout/layout_webview.xml
                val view = LayoutInflater.from(context).inflate(R.layout.layout_webview, null) as WebView
                view.apply {
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                }
            },
            update = { webView ->
                if (webView.url != viewModel.url) {
                    webView.loadUrl(viewModel.url)
                }
            }
        )
    }
}

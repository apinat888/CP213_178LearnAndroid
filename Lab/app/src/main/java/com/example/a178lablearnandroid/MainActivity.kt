package com.example.a178lablearnandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier.fillMaxSize()
                .background(color=Color.Cyan)
                .padding(32.dp))  {
                val context = LocalContext.current
                var imageUri by remember { mutableStateOf<Uri?>(null) }

                val galleryLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    imageUri = uri
                }

                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean ->
                    if (isGranted) {
                        galleryLauncher.launch("image/*")
                    }
                }

                //hp
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(color=Color.White)
                ) {
                    Text(
                        text = "HP",
                        modifier = Modifier.align(alignment= Alignment.CenterStart)
                            .fillMaxWidth(fraction = 0.78f)
                            .background(color=Color.LightGray)
                            .padding(8.dp)


                    )


                }

                //image
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(300.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(top= 16.dp)
                            .clickable {
                                context.startActivity(Intent(context, MainActivity2::class.java))
                            }
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.thomas),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(300.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(top= 16.dp)
                            .clickable {
                                context.startActivity(Intent(context, MainActivity2::class.java))
                            }
                    )
                }

                Button(
                    onClick = {
                        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            android.Manifest.permission.READ_MEDIA_IMAGES
                        } else {
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        }

                        if (androidx.core.content.ContextCompat.checkSelfPermission(context, permission) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                            galleryLauncher.launch("image/*")
                        } else {
                            permissionLauncher.launch(permission)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp)
                ) {
                    Text("เลือกรูปภาพ")
                }


                //status
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween

                )
                {
                    var str:Int by remember { mutableStateOf(10) }
                    var agi:Int by remember { mutableStateOf(10) }
                    var int:Int by remember { mutableStateOf(10) }
                    Column() {
                        Button(onClick = {str = str+1 }) {
                            Text(text = "+", fontSize = 32.sp)
                        }

                        Text(text = "Str", fontSize = 32.sp)
                        Text(text = str.toString(), fontSize = 32.sp)
//                        Text(text ="-",fontSize=32.sp,
//                            modifier = Modifier.clickable {
//                                str = str-1
//                            })
                        Button(onClick = {str = str-1 }) {
                            Text(text = "-", fontSize = 32.sp)
                        }

                    }
                    Column() {
                        Button(onClick = {agi = agi+1 }) {
                            Text(text = "+", fontSize = 32.sp)
                        }

                        Text(text = "Agi", fontSize = 32.sp)
                        Text(text = agi.toString(), fontSize = 32.sp)

                        Button(onClick = {agi = agi-1 }) {
                            Text(text = "-", fontSize = 32.sp)
                        }
                    }
                    Column() {
                        Button(onClick = {int = int+10 }) {
                            Text(text = "+", fontSize = 32.sp)
                        }

                        Text(text = "Int", fontSize = 32.sp)
                        Text(text = int.toString(), fontSize = 32.sp)

                        Button(onClick = {int = int-1 }) {
                            Text(text = "-", fontSize = 32.sp)
                        }
                    }

                }










            }
        }
    }
    override fun onStart() {
        super.onStart()
        Log.i("Lifecycle", "MainActivity : onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Lifecycle", "MainActivity : onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Lifecycle", "MainActivity : onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Lifecycle", "MainActivity : onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Lifecycle", "MainActivity : onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("Lifecycle", "MainActivity : onRestart")
    }


}


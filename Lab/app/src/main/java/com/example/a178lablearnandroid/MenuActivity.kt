package com.example.a178lablearnandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                MenuButton(
                    title = "RPGCardActivity",
                    transitionType = "Slide Right to Left",
                    onClick = {
                        navigateTo(MainActivity::class.java, R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                )
                MenuButton(
                    title = "PokedexActivity",
                    transitionType = "Slide Left to Right",
                    onClick = {
                        navigateTo(MainActivity2::class.java, R.anim.slide_in_left, R.anim.slide_out_right)
                    }
                )
                MenuButton(
                    title = "LifeCycleComposeActivity",
                    transitionType = "Fade In/Out",
                    onClick = {
                        navigateTo(MenuActivity::class.java, R.anim.fade_in, R.anim.fade_out)
                    }
                )
                MenuButton(
                    title = "PokedexActivityv2",
                    transitionType = "System Default",
                    onClick = {
                        startActivity(Intent(this@MenuActivity, PokedexActivity::class.java))
                    }
                )
                MenuButton(
                    title = "Part1Activity",
                    transitionType = "Slide Right to Left",
                    onClick = {
                        navigateTo(Part1Activity::class.java, R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                )
                MenuButton(
                    title = "Part2Activity",
                    transitionType = "Slide Left to Right",
                    onClick = {
                        navigateTo(Part2Activity::class.java, R.anim.slide_in_left, R.anim.slide_out_right)
                    }
                )
                MenuButton(
                    title = "Part3Activity",
                    transitionType = "Fade In/Out",
                    onClick = {
                        navigateTo(Part3Activity::class.java, R.anim.fade_in, R.anim.fade_out)
                    }
                )
                MenuButton(
                    title = "Part4Activity",
                    transitionType = "Slide Right to Left",
                    onClick = {
                        navigateTo(Part4Activity::class.java, R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                )
                MenuButton(
                    title = "Part5Activity",
                    transitionType = "Fade In/Out",
                    onClick = {
                        navigateTo(Part5Activity::class.java, R.anim.fade_in, R.anim.fade_out)
                    }
                )
                MenuButton(
                    title = "Part6Activity",
                    transitionType = "Slide Left to Right",
                    onClick = {
                        navigateTo(Part6Activity::class.java, R.anim.slide_in_left, R.anim.slide_out_right)
                    }
                )
                MenuButton(
                    title = "Part8Activity",
                    transitionType = "Slide Left to Right",
                    onClick = {
                        navigateTo(Part8Activity::class.java, R.anim.slide_in_left, R.anim.slide_out_right)
                    }
                )
                MenuButton(
                    title = "Part9Activity",
                    transitionType = "Slide Left to Right",
                    onClick = {
                        navigateTo(Part9Activity::class.java, R.anim.slide_in_left, R.anim.slide_out_right)
                    }
                )
                MenuButton(
                    title = "Part10Activity",
                    transitionType = "Slide Left to Right",
                    onClick = {
                        navigateTo(Part10Activity::class.java, R.anim.slide_in_left, R.anim.slide_out_right)
                    }
                )
                MenuButton(
                    title = "Part11Activity",
                    transitionType = "Slide Left to Right",
                    onClick = {
                        navigateTo(Part11Activity::class.java, R.anim.slide_in_left, R.anim.slide_out_right)
                    }
                )
                MenuButton(
                    title = "Part12Activity",
                    transitionType = "Slide Left to Right",
                    onClick = {
                        navigateTo(Part12Activity::class.java, R.anim.slide_in_left, R.anim.slide_out_right)
                    }
                )
            }
        }
    }

    private fun navigateTo(activityClass: Class<*>, enterAnim: Int, exitAnim: Int) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
        overridePendingTransition(enterAnim, exitAnim)
    }
}

@Composable
fun MenuButton(title: String, transitionType: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(vertical = 2.dp) // ลบ fillMaxWidth เพื่อให้ชิดซ้าย
    ) {
        Column {
            Text(text = title)
            Text(
                text = "Transition: $transitionType",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

package com.example.top10bars.ui.screens.profile

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.top10bars.data.local.FavoritesManager
import com.example.top10bars.data.local.NotificationManager
import com.example.top10bars.data.local.ThemeManager
import com.example.top10bars.data.local.UserManager
import com.example.top10bars.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    favoritesManager: FavoritesManager,
    themeManager: ThemeManager,
    userManager: UserManager,
    notificationManager: NotificationManager
) {
    val favorites by favoritesManager.favoritesIds.collectAsState(initial = emptySet())
    val userProfile by userManager.userProfile.collectAsState()
    val isDarkTheme by themeManager.isDarkTheme.collectAsState()
    val notificationsEnabled by notificationManager.notificationsEnabled.collectAsState()
    val context = LocalContext.current

    var showEditDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var tempName by remember { mutableStateOf("") }
    var tempEmail by remember { mutableStateOf("") }

    // Dialog แก้ไขโปรไฟล์
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("แก้ไขโปรไฟล์") },
            text = {
                Column {
                    OutlinedTextField(
                        value = tempName,
                        onValueChange = { tempName = it },
                        label = { Text("ชื่อ") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tempEmail,
                        onValueChange = { tempEmail = it },
                        label = { Text("อีเมล") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    userManager.updateProfile(tempName, tempEmail)
                    showEditDialog = false
                }) {
                    Text("บันทึก")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("ยกเลิก")
                }
            }
        )
    }

    // Dialog เกี่ยวกับแอป
    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            title = { Text("เกี่ยวกับแอป NightPick") },
            text = {
                Text(
                    text = "NightPick เวอร์ชัน 1.0\n\n" +
                           "แอปพลิเคชันสำหรับค้นหาบาร์และร้านอาหารที่ดีที่สุดในกรุงเทพฯ " +
                           "พร้อมระบบแนะนำร้านแบบเรียลไทม์ นำทาง และสุ่มร้านเพื่อช่วยคุณตัดสินใจ " +
                           "สนุกกับค่ำคืนของคุณไปกับเรา!\n\n" +
                           "พัฒนาโดย: 178_maibok ิ ิ",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(onClick = { showAboutDialog = false }) {
                    Text("ตกลง")
                }
            }
        )
    }

    // Dialog ยืนยันออกจากระบบ
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("ออกจากระบบ") },
            text = { Text("คุณต้องการออกจากระบบและรีเซ็ตข้อมูลทั้งหมดหรือไม่?") },
            confirmButton = {
                Button(
                    onClick = {
                        userManager.updateProfile("Guest User", "guest@nightpick.com")
                        favoritesManager.clearAll()
                        showLogoutDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color.Red)
                ) {
                    Text("ยืนยัน", color = androidx.compose.ui.graphics.Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("ยกเลิก")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Profile Image
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, GoldAccent, CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // User Info
        Text(
            text = userProfile.name,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = userProfile.email,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Stats Card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            StatItem(label = "ร้านโปรด", value = "${favorites.size}")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Settings Options
        Column(modifier = Modifier.fillMaxWidth()) {
            
            // Theme Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Filled.Nightlight else Icons.Filled.LightMode,
                        contentDescription = null,
                        tint = PurpleAccent,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = if (isDarkTheme) "โหมดมืด (Dark Mode)" else "โหมดสว่าง (Light Mode)",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { themeManager.toggleTheme() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = GoldAccent,
                        checkedTrackColor = PurpleAccent
                    )
                )
            }

            ProfileOptionItem(
                icon = Icons.Filled.Settings, 
                label = "ตั้งค่าบัญชี (แก้ไขชื่อ/อีเมล)",
                onClick = {
                    tempName = userProfile.name
                    tempEmail = userProfile.email
                    showEditDialog = true
                }
            )

            // Notifications Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = null,
                        tint = PurpleAccent,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "การแจ้งเตือน",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationManager.toggleNotifications() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = GoldAccent,
                        checkedTrackColor = PurpleAccent
                    )
                )
            }

            ProfileOptionItem(
                icon = Icons.Filled.Share,
                label = "แชร์แอป NightPick",
                onClick = {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "แนะนำแอป NightPick! รวมร้านบาร์สุดฮิปในกรุงเทพฯ พร้อมระบบนำทางและเช็คสถานะร้านแบบเรียลไทม์ โหลดเลย!")
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                }
            )
            
            ProfileOptionItem(
                icon = Icons.Filled.Info, 
                label = "เกี่ยวกับแอป NightPick",
                onClick = { showAboutDialog = true }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { showLogoutDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("ออกจากระบบ", color = androidx.compose.ui.graphics.Color.Red)
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
        Text(text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun ProfileOptionItem(icon: ImageVector, label: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = PurpleAccent, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

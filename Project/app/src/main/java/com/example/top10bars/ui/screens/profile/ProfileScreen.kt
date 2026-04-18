package com.example.top10bars.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.top10bars.data.local.FavoritesManager
import com.example.top10bars.ui.theme.*

@Composable
fun ProfileScreen(favoritesManager: FavoritesManager) {
    val favorites by favoritesManager.favoritesIds.collectAsState(initial = emptySet())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
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
                .background(DarkSurface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = TextSecondary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // User Info
        Text(
            text = "Guest User",
            style = MaterialTheme.typography.headlineMedium,
            color = TextPrimary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "guest@nightpick.com",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Stats Card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(DarkSurface)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            StatItem(label = "ร้านโปรด", value = "${favorites.size}")
            StatItem(label = "รีวิว", value = "0")
            StatItem(label = "เช็คอิน", value = "0")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Settings Options
        Column(modifier = Modifier.fillMaxWidth()) {
            ProfileOptionItem(icon = Icons.Filled.Settings, label = "ตั้งค่าบัญชี")
            ProfileOptionItem(icon = Icons.Filled.Notifications, label = "การแจ้งเตือน")
            ProfileOptionItem(icon = Icons.Filled.Info, label = "เกี่ยวกับแอป NightPick")
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* Logout action */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = DarkSurface),
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
        Text(text = label, fontSize = 12.sp, color = TextSecondary)
    }
}

@Composable
fun ProfileOptionItem(icon: ImageVector, label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = PurpleAccent, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = TextSecondary)
    }
}

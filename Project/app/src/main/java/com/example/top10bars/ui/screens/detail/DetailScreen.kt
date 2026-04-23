package com.example.top10bars.ui.screens.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.top10bars.ui.theme.*
import com.example.top10bars.utils.BarStatus
import com.example.top10bars.utils.getRealTimeStatus
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onBack: () -> Unit
) {
    val bar by viewModel.bar.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()
    val context = LocalContext.current

    if (bar == null) {
        Box(modifier = Modifier.fillMaxSize().background(DarkBackground), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = GoldAccent)
        }
        return
    }

    val b = bar!!

    var status by remember { mutableStateOf(getRealTimeStatus(b.openTime, b.closeTime)) }
    LaunchedEffect(b) {
        while(true) {
            status = getRealTimeStatus(b.openTime, b.closeTime)
            delay(60000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // Top Toolbar
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
            }
            Spacer(modifier = Modifier.weight(1f))
            
            // Share Button
            IconButton(onClick = {
                val shareText = """
                    ไปร้านนี้กัน! ${b.name} (${b.type})
                    ที่อยู่: ${b.address}
                    เบอร์โทร: ${b.phone}
                    (ส่งจากแอป NightPick)
                """.trimIndent()
                
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, "แชร์ร้าน ${b.name} ให้เพื่อน")
                context.startActivity(shareIntent)
            }) {
                Icon(Icons.Filled.Share, contentDescription = "Share", tint = TextPrimary)
            }

            IconButton(onClick = { viewModel.toggleFavorite() }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = HeartRed,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            // Gallery
            val pagerState = rememberPagerState(pageCount = { b.imageUrls.size.coerceAtLeast(1) })
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth().height(250.dp)) { page ->
                AsyncImage(
                    model = b.imageUrls.getOrNull(page),
                    contentDescription = "Gallery Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Dot Indicators
            if(b.imageUrls.size > 1) {
                Row(
                    Modifier.wrapContentHeight().fillMaxWidth().padding(bottom = 8.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(b.imageUrls.size) { iteration ->
                        val color = if (pagerState.currentPage == iteration) GoldAccent else TextSecondary
                        Box(modifier = Modifier.padding(2.dp).clip(CircleShape).background(color).size(8.dp))
                    }
                }
            }

            // Info Section
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(text = b.name, style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Star, contentDescription = "Rating", tint = GoldAccent, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = b.rating.toString(), style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))

                // Status
                val (statusColor, statusText) = when(status) {
                    BarStatus.OPEN -> StatusOpen to "เปิดอยู่"
                    BarStatus.CLOSED -> StatusClosed to "ปิดแล้ว"
                    BarStatus.CLOSING_SOON -> StatusClosingSoon to "ใกล้ปิด"
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(color = statusColor, shape = RoundedCornerShape(4.dp)) {
                        Text(text = statusText, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), style = MaterialTheme.typography.bodySmall, color = TextPrimary)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "${b.openTime} - ${b.closeTime}", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = b.priceLevel, style = MaterialTheme.typography.bodyMedium, color = GoldAccent, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Address
                Text(text = b.address, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                
                Spacer(modifier = Modifier.height(24.dp))

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = {
                            val uri = Uri.parse("geo:${b.locationLat},${b.locationLng}?q=${b.locationLat},${b.locationLng}(${b.name})")
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            intent.setPackage("com.google.android.apps.maps")
                            if (intent.resolveActivity(context.packageManager) != null) {
                                context.startActivity(intent)
                            } else {
                                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                            }
                        },
                        modifier = Modifier.weight(1f).height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = StatusOpen)
                    ) {
                        Icon(Icons.Filled.LocationOn, contentDescription = "Navigate", tint = TextPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("นำทาง", color = TextPrimary)
                    }

                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${b.phone}"))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.weight(1f).height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PurpleAccent)
                    ) {
                        Icon(Icons.Filled.Call, contentDescription = "Call", tint = TextPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("โทรจอง", color = TextPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Signature Drinks
                Text(text = "Signature Drinks", style = MaterialTheme.typography.titleLarge, color = GoldAccent)
                Spacer(modifier = Modifier.height(8.dp))
                b.signatureDrinks.forEach { drink ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "• ${drink.name}", color = TextPrimary)
                        Text(text = "${drink.price} ฿", color = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Music Vibe
                Text(text = "Music Vibe", style = MaterialTheme.typography.titleLarge, color = GoldAccent)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = b.musicVibe, color = TextPrimary)
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

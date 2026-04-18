package com.example.top10bars.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bar(
    val id: String,
    val name: String,
    val description: String,
    val imageUrls: List<String>,
    val type: String, // Rooftop, Jazz, Cocktail, Club, Speakeasy
    val locationLat: Double,
    val locationLng: Double,
    val address: String,
    val rating: Float,
    val priceLevel: String, // ฿, ฿฿, ฿฿฿
    val phone: String,
    val signatureDrinks: List<MenuItem>,
    val musicVibe: String,
    val openTime: String, // format "HH:mm" e.g. "18:00"
    val closeTime: String // format "HH:mm" e.g. "01:00"
) : Parcelable

@Parcelize
data class MenuItem(
    val name: String,
    val price: Int
) : Parcelable

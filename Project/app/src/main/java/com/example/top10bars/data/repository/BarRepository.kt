package com.example.top10bars.data.repository

import com.example.top10bars.data.model.Bar
import com.example.top10bars.data.model.MenuItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BarRepository {
    // Simulated mock delay
    suspend fun getBars(): List<Bar> {
        delay(800) // fake network delay
        return mockBars
    }
    
    fun getBarsFlow(): Flow<List<Bar>> = flow {
        emit(mockBars)
    }

    private val mockBars = listOf(
        Bar(
            id = "1",
            name = "Vesper Bar",
            description = "Classic cocktail bar with a modern twist.",
            imageUrls = listOf("https://images.unsplash.com/photo-1514362545857-3bc16c4c7d1b", "https://images.unsplash.com/photo-1470337458703-46ad1756a187"),
            type = "Cocktail",
            locationLat = 13.725900,
            locationLng = 100.533200,
            address = "Convent Rd, Silom, Bang Rak, Bangkok 10500",
            rating = 4.8f,
            priceLevel = "฿฿฿",
            phone = "02-235-2777",
            signatureDrinks = listOf(MenuItem("Vesper Martini", 450), MenuItem("Negroni", 400)),
            musicVibe = "Lounge & Chill",
            openTime = "18:00",
            closeTime = "01:00"
        ),
        Bar(
            id = "2",
            name = "Rabbit Hole",
            description = "Premium speakeasy bar hidden in Thong Lo.",
            imageUrls = listOf("https://images.unsplash.com/photo-1542556398-e7c617b07db2"),
            type = "Speakeasy",
            locationLat = 13.729900,
            locationLng = 100.581500,
            address = "125 Sukhumvit 55 (Thong Lo), Khlong Tan Nuea, Watthana, Bangkok 10110",
            rating = 4.7f,
            priceLevel = "฿฿฿",
            phone = "098-532-3500",
            signatureDrinks = listOf(MenuItem("Smoked Old Fashioned", 420), MenuItem("Truffle Martini", 480)),
            musicVibe = "Deep House",
            openTime = "19:00",
            closeTime = "02:00"
        ),
        Bar(
            id = "3",
            name = "Teens of Thailand",
            description = "The first gin bar in Thailand, located in Soi Nana.",
            imageUrls = listOf("https://images.unsplash.com/photo-1436018626274-89acd1d6ec9d"),
            type = "Cocktail",
            locationLat = 13.738800,
            locationLng = 100.514000,
            address = "76 Soi Nana, Charoen Krung Rd, Pom Prap, Bangkok 10100",
            rating = 4.6f,
            priceLevel = "฿฿",
            phone = "096-848-0847",
            signatureDrinks = listOf(MenuItem("Thai Tea Gin Tonic", 350), MenuItem("Chrysanthemum Gin", 380)),
            musicVibe = "Indie & Alternative",
            openTime = "18:00",
            closeTime = "00:00"
        ),
        Bar(
            id = "4",
            name = "Sky Bar",
            description = "Iconic rooftop bar located at Lebua.",
            imageUrls = listOf("https://images.unsplash.com/photo-1551024709-8f23befc6f87"),
            type = "Rooftop",
            locationLat = 13.721400,
            locationLng = 100.517000,
            address = "1055 Silom Rd, Bang Rak, Bangkok 10500",
            rating = 4.5f,
            priceLevel = "฿฿฿",
            phone = "02-624-9555",
            signatureDrinks = listOf(MenuItem("Hangovertini", 550), MenuItem("Braveheart", 500)),
            musicVibe = "Live Jazz",
            openTime = "17:00",
            closeTime = "01:00"
        ),
        Bar(
            id = "5",
            name = "Iron Fairies",
            description = "Whimsical jazz bar with magical interior.",
            imageUrls = listOf("https://images.unsplash.com/photo-1563298723-dcfebaa392e3"),
            type = "Jazz",
            locationLat = 13.730300,
            locationLng = 100.581500,
            address = "402 Soi Thong Lo, Khlong Tan Nuea, Watthana, Bangkok 10110",
            rating = 4.6f,
            priceLevel = "฿฿",
            phone = "099-283-9000",
            signatureDrinks = listOf(MenuItem("Fairy Dust", 380), MenuItem("Smoke in a Bottle", 420)),
            musicVibe = "Live Jazz & Blues",
            openTime = "18:00",
            closeTime = "02:00"
        ),
        Bar(
            id = "6",
            name = "Maggie Choo's",
            description = "Immersive lounge with an Asian 1930s theme.",
            imageUrls = listOf("https://images.unsplash.com/photo-1574096079513-d8259312b78a"),
            type = "Lounge",
            locationLat = 13.723500,
            locationLng = 100.521800,
            address = "Novotel Fenix Silom, 320 Silom Rd, Bang Rak, Bangkok 10500",
            rating = 4.5f,
            priceLevel = "฿฿฿",
            phone = "091-772-2144",
            signatureDrinks = listOf(MenuItem("Shangai Girl", 400), MenuItem("Red Lotus", 390)),
            musicVibe = "Jazz & DJ Sets",
            openTime = "19:30",
            closeTime = "02:00"
        ),
        Bar(
            id = "7",
            name = "Sugar Ray",
            description = "Hip rooftop and speakeasy venue.",
            imageUrls = listOf("https://images.unsplash.com/photo-1510626176961-4b57d4fbad03"),
            type = "Rooftop",
            locationLat = 13.730200,
            locationLng = 100.569400,
            address = "88 Sukhumvit 24 Alley, Khlong Tan, Khlong Toei, Bangkok 10110",
            rating = 4.4f,
            priceLevel = "฿฿",
            phone = "094-417-9898",
            signatureDrinks = listOf(MenuItem("Sugar Rush", 350), MenuItem("Midnight Sun", 360)),
            musicVibe = "Hip-Hop & R&B",
            openTime = "17:00",
            closeTime = "00:00"
        ),
        Bar(
            id = "8",
            name = "Havana Social",
            description = "Secret Cuban-themed club.",
            imageUrls = listOf("https://images.unsplash.com/photo-1566417713940-fe7c737a9ef2"),
            type = "Speakeasy",
            locationLat = 13.743100,
            locationLng = 100.556200,
            address = "Sukhumvit Soi 11, Khlong Toei Nuea, Watthana, Bangkok 10110",
            rating = 4.7f,
            priceLevel = "฿฿",
            phone = "087-066-7711",
            signatureDrinks = listOf(MenuItem("Mojito", 320), MenuItem("Cuba Libre", 300)),
            musicVibe = "Latin, Salsa & Reggaeton",
            openTime = "19:00",
            closeTime = "02:00"
        ),
        Bar(
            id = "9",
            name = "Sing Sing Theater",
            description = "Cinematic club with top DJs and Chinese motif.",
            imageUrls = listOf("https://images.unsplash.com/photo-1561501878-aabd62634533"),
            type = "Club",
            locationLat = 13.731400,
            locationLng = 100.573200,
            address = "Sukhumvit 45 Alley, Khlong Tan Nuea, Watthana, Bangkok 10110",
            rating = 4.6f,
            priceLevel = "฿฿฿",
            phone = "063-225-1331",
            signatureDrinks = listOf(MenuItem("Sing Sing Sour", 400), MenuItem("Dragon Breath", 450)),
            musicVibe = "EDM & House",
            openTime = "21:00",
            closeTime = "02:00"
        ),
        Bar(
            id = "10",
            name = "Saxophone Pub",
            description = "Legendary live jazz and blues venue.",
            imageUrls = listOf("https://images.unsplash.com/photo-1510464245749-bb17208e2ec1"),
            type = "Jazz",
            locationLat = 13.763400,
            locationLng = 100.538100,
            address = "3/8 Victory Monument, Phaya Thai, Bangkok 10400",
            rating = 4.8f,
            priceLevel = "฿",
            phone = "02-246-5472",
            signatureDrinks = listOf(MenuItem("Margarita", 250), MenuItem("Long Island", 280), MenuItem("Classic Mojito", 250)),
            musicVibe = "Live Jazz & Blues",
            openTime = "18:00",
            closeTime = "01:30"
        )
    )
}

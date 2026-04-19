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
            imageUrls = listOf("https://dynamic-media-cdn.tripadvisor.com/media/photo-o/17/3f/68/21/vesper-bar.jpg?w=900&h=500&s=1"),
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
            imageUrls = listOf("https://static1.squarespace.com/static/5a5320c8e45a7c0deb541657/t/5a532fc8c830256b61bc2fab/1515401167131/S__13082642.jpg"),
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
            imageUrls = listOf("https://www.theworlds50best.com/discovery/filestore/jpg/TeensOfThailand-Bangkok-Thailand-01.jpg"),
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
            imageUrls = listOf("https://lebua.com/wp-content/uploads/2019/04/48-Sky-Bar-Angle-1-scaled.jpg"),
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
            imageUrls = listOf("https://fuzion.co.th/wp-content/uploads/2025/03/iron-fairies-music-bar-002.webp"),
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
            imageUrls = listOf("https://luxecityguides.com/wp-content/uploads/2021/04/110018131774-didd2Bo.original.jpg"),
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
            imageUrls = listOf("https://static.bkkmenu.com/files/2018/07/Sugarrays4-1005x670.jpg"),
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
            imageUrls = listOf("https://wnfdiary.com/wp-content/uploads/2019/01/havana-social-bangkok-7.jpg"),
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
            imageUrls = listOf("https://www.ashleysuttondesign.com/wp-content/uploads/2016/12/Sing-Sing-Theater-1.jpg"),
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
            imageUrls = listOf("https://dynamic-media-cdn.tripadvisor.com/media/photo-o/24/bf/58/8f/caption.jpg?w=1200&h=1200&s=1"),
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
        ),
        Bar(
            id = "11", // ตั้ง ID ให้ไม่ซ้ำกับร้านอื่น
            name = "Widdershins",
            description = "A mystical retreat where time turns counter-clockwise into the world of the occult.",
            imageUrls = listOf("https://siam2nite.media/-GarGPoYnMNuec_Xk0iMLePZCXY=/1280x960/smart/locations/3082/cover_large_p1f287pv5o1f301aer16991vtb1e5m5.jpg"),
            type = "Cocktail", // เช่น Rooftop, Cocktail, Jazz
            locationLat = 13.74048, // ละติจูด (หาได้จาก Google Maps)
            locationLng = 100.51084, // ลองจิจูด
            address = "2, 438 Yaowarat Rd, Samphanthawong, Bangkok 10100",
            rating = 4.7f,
            priceLevel = "฿฿", // ระดับราคา
            phone = "095-987-9239",
            signatureDrinks = listOf(
                MenuItem("Naturals", 550),
                MenuItem("Jack's Favourite", 350)
            ),
            musicVibe = "Jazz & Blues",
            openTime = "18:00",
            closeTime = "00:00"
        ),
        Bar(
                id = "12", // ตั้ง ID ให้ไม่ซ้ำกับร้านอื่น
        name = "mil social club",
        description = "A vibrant social hub blending modern art deco aesthetics with craft spirits and a lively community vibe.",
        imageUrls = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5Lh3nAxDA7pYz_NpWtnx1dHfFMAjreSGOzA&s"),
        type = "Club", // เช่น Rooftop, Cocktail, Jazz
        locationLat = 13.73483, // ละติจูด (หาได้จาก Google Maps)
        locationLng = 100.58185, // ลองจิจูด
        address = "3rd Floor, Market Place, 15 Thong Lo, Khlong Tan Nuea, Watthana, Bangkok 10110",
        rating = 4.5f,
        priceLevel = "฿฿฿", // ระดับราคา
        phone = "091-549-4426",
        signatureDrinks = listOf(
            MenuItem("Space", 550),
            MenuItem("Elledecor", 650)
        ),
        musicVibe = "Hip-Hop,R&B",
        openTime = "20:30",
        closeTime = "03:30"
        )
    )
}

package org.apinat.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
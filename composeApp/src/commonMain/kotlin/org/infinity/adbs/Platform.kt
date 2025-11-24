package org.infinity.adbs

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
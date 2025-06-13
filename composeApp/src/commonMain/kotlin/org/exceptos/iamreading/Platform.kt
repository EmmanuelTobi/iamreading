package org.exceptos.iamreading

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
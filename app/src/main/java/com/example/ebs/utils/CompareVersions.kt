package com.example.ebs.utils

fun compareVersions(v1: String, v2: String): Int {
    val parts1 = v1.split("-", limit = 2)[0].split(".")
    val parts2 = v2.split("-", limit = 2)[0].split(".")
    val length = maxOf(parts1.size, parts2.size)
    for (i in 0 until length) {
        val p1 = parts1.getOrNull(i)?.toIntOrNull() ?: 0
        val p2 = parts2.getOrNull(i)?.toIntOrNull() ?: 0
        if (p1 != p2) return p1 - p2
    }
    return 0
}
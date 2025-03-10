package com.example.ebs.utils

fun extractRouteName(route: Any?): String? {
    val regex = Regex("""Route(?:\.|\$)([A-Za-z0-9_]+)""")
    return route?.toString()?.let { regex.find(it)?.groupValues?.get(1) }
}
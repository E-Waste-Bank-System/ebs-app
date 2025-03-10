package com.example.ebs.ui.navigation.destinations

data class NavigationDestination<T : Any>(
    val name: String,
    val route: T,
    val icon: Int? = null
)
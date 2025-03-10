package com.example.ebs.ui.navigation.destinations

import kotlinx.serialization.Serializable

sealed class Route {
    @Serializable
    object Welcome
    @Serializable
    object SignIn
    @Serializable
    object SignUp
    @Serializable
    object Notifikasi
    @Serializable
    object Detail
    @Serializable
    object Dashboard
    @Serializable
    object Scan
    @Serializable
    object Riwayat
    @Serializable
    object Profile
    @Serializable
    object Settings
}
package com.example.ebs.ui.navigation.destinations

import com.example.ebs.R

val NavigationDestinationList = listOf(
    NavigationDestination("Welcome", Route.Welcome),
    NavigationDestination("SignUp", Route.SignUp),
    NavigationDestination("SignIn", Route.SignIn),
    NavigationDestination("Detail", Route.Detail),
    NavigationDestination("Notifikasi", Route.Notifikasi),
    NavigationDestination("Home", Route.Dashboard, R.drawable.home_variant_outline),
    NavigationDestination("Scan", Route.Scan, R.drawable.scan_helper),
    NavigationDestination("Riwayat", Route.Riwayat, R.drawable.view_list_outline),
    NavigationDestination("Profile", Route.Profile, R.drawable.account),
    NavigationDestination("Settings", Route.Settings)
)
package com.example.ebs.ui.navigation.destinations

import com.example.ebs.R

val MainNavbar = listOf(
    NavigationDestination("Home", Route.Dashboard, R.drawable.home_variant_outline),
    NavigationDestination("Scan", Route.Scan, R.drawable.scan_helper),
    NavigationDestination("Riwayat", Route.Riwayat, R.drawable.view_list_outline),
    NavigationDestination("Profile", Route.Profile, R.drawable.account)
)
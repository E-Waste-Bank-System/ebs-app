package com.example.ebs.ui.screens.detail

data class ScanResult(
    val category: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val riskLvl: Int = 0,
    val regressionResult: Int = 0,
    val suggestion: List<String> = emptyList(),
    val total: Int = 0
)
package com.example.ebs.ui.dialogues

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ebs.R
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.dialogues.bases.ProfileDialogue

@Composable
fun Lokasi(navController: NavController) {
    ProfileDialogue("Lokasi",R.drawable.recycle,navController) {
        TextTitleS(
            "Saat Ini Lokasi Pengolah Sampah belum bisa diakses ✌\uFE0F",
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)
        )
    }
}
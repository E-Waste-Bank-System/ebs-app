package com.example.ebs.ui.dialogues

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ebs.R
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.dialogues.bases.ProfileDialogue

@Composable
fun BeriNilai(navController: NavController) {
    val uriHandler = LocalUriHandler.current
    ProfileDialogue("Beri Nilai",R.drawable.recycle,navController) {
        TextTitleS(
            "Silakan Nilai Aplikasi Kami \uD83D\uDE0A",
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)
        )
        TextTitleS(
            buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("Feedback Link")
                }
            },
            modifier = Modifier
                .clickable {
                    uriHandler.openUri("https://forms.gle/eu7Kpv1jw47oQGWa9")
                }
        )
    }
}
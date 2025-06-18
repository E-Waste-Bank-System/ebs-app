package com.example.ebs.ui.dialogues

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ebs.R
import com.example.ebs.ui.components.shapes.MyIcon
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextTitleS

@Composable
fun BeriNilai(navController: NavController) {
    val uriHandler = LocalUriHandler.current
    Card {
        CenterColumn(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 20.dp)
        ) {
            CenterRow(
                hArr = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                CenterRow(
                    hArr = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                ) {
                    Text(text = "Smartphone")
                    MyIcon(
                        painterResource(id = R.drawable.recycle),
                        contentDescription = "avItem.name"
                    )
                }
                MyIcon(
                    painterResource(id = R.drawable.close),
                    contentDescription = "avItem.name",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
            }
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
}
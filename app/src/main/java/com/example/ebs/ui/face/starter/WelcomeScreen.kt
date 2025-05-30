package com.example.ebs.ui.face.starter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.ebs.R
import com.example.ebs.ui.face.components.structures.CenterColumn
import com.example.ebs.ui.face.components.structures.CenterRow
import com.example.ebs.ui.face.components.texts.TextContentM
import com.example.ebs.ui.face.components.texts.TextTitleM
import com.example.ebs.ui.navigation.NavigationHandler

@Composable
fun WelcomeScreen(
    navHandler: NavigationHandler
) {
    CenterColumn(
        vArr = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 22.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(0.90f)
                .fillMaxHeight(0.45f)

        ) {
            Image(
                painter = painterResource(R.drawable.backgron),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Image(
                painter = painterResource(id = R.drawable.logos),
                contentDescription = "E-Waste Illustration",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(216.dp)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        CenterColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ){
            TextTitleM(buildAnnotatedString {
                append(stringResource(R.string.open))
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(stringResource(R.string.open_n1))
                }
                append(",")
            }, mod = true)
            TextTitleM(stringResource(R.string.open_n2))

            Spacer(modifier = Modifier.height(8.dp))

            TextContentM(stringResource(R.string.open_n3))
            TextContentM(stringResource(R.string.open_n4))
            TextContentM(stringResource(R.string.open_n5))

            Spacer(modifier = Modifier.height(48.dp))

            CenterRow(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ){
                Button(
                    onClick = { navHandler.signInFromWelcome() },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    modifier = Modifier
                        .padding(5.dp)
                        .height(50.dp)
                        .width(140.dp)
                ) {
                    Text(stringResource(R.string.login))
                }

                Button(
                    onClick = { navHandler.signUpFromWelcome() },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    modifier = Modifier
                        .padding(5.dp)
                        .height(50.dp)
                        .width(140.dp)
                ) {
                    Text(stringResource(R.string.daftar))
                }
            }

            Spacer(modifier = Modifier.height(64.dp))

        }
    }
}


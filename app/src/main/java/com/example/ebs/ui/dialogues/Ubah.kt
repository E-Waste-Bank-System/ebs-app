package com.example.ebs.ui.dialogues

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.ebs.R
import com.example.ebs.data.repositories.UserPreferencesRepository
import com.example.ebs.ui.components.inputs.AestheticButton
import com.example.ebs.ui.components.inputs.InputSpace
import com.example.ebs.ui.components.shapes.MyIcon
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.screens.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Ubah(
    viewModelAuth: MainViewModel,
    userPref: UserPreferencesRepository
) {
    val namaBaru = remember { mutableStateOf(viewModelAuth.localInfo.name ?: "") }
    val waitEmail = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
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
                    TextTitleS(text = "Ubah Profil")
                    MyIcon(
                        painterResource(id = R.drawable.recycle),
                        contentDescription = "avItem.name",
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                }
                MyIcon(
                    painterResource(id = R.drawable.close),
                    contentDescription = "avItem.name"
                )
            }
            InputSpace(
                namaBaru.value,
                namaBaru,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(0.9f)
            )
            AestheticButton(
                onClick = {
                    viewModelAuth.firstOpen = true
                    waitEmail.value = true
                    scope.launch {
                        userPref.saveName(namaBaru.value)
                        delay(1000)
                        waitEmail.value = false
                        viewModelAuth.navHandler.back()
                        viewModelAuth.navHandler.dashboard()
                    }
                },
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(0.9f)
                    .padding(8.dp)
                    .height(55.dp)
            ){
                if (!waitEmail.value) {
                    TextTitleS(
                        buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.White)) {
                                append("Simpan")
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Center)
                    )
                } else {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .align(Center)
                    )
                }
            }
        }
    }
}
package com.example.ebs.ui.face.starter

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ebs.R
import com.example.ebs.ui.face.components.inputs.AestheticButton
import com.example.ebs.ui.face.components.structures.CenterColumn
import com.example.ebs.ui.face.components.structures.CenterRow
import com.example.ebs.ui.face.components.inputs.InputSpace
import com.example.ebs.ui.face.components.texts.TextContentM
import com.example.ebs.ui.face.components.texts.TextTitleL
import com.example.ebs.ui.navigation.NavigationHandler

@Composable
fun SignUpScreen(
    navHandler: NavigationHandler,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val exitDialogue = remember { mutableStateOf(false) }

    BackHandler { exitDialogue.value = true }

    if (exitDialogue.value) {
        navHandler.exitDialogue()
        exitDialogue.value = false
    }

    CenterColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        TextTitleL(stringResource(R.string.sign_up))

        Spacer(modifier = Modifier.height(32.dp))

        InputSpace(stringResource(R.string.username))
        InputSpace(stringResource(R.string.email))
        InputSpace(stringResource(R.string.notel))
        InputSpace(stringResource(R.string.password))

        Spacer(modifier = Modifier.height(16.dp))

        AestheticButton(
            text = stringResource(R.string.daftar),
            onClick = { navHandler.menuFromSignUp() }
        )

        Spacer(modifier = Modifier.height(32.dp))

        CenterRow {
            TextContentM(stringResource(R.string.sudahPunyaAkun))
            TextContentM(
                buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(stringResource(R.string.login))
                    }
                },
                mod = true,
                modifier = Modifier
                    .clickable { navHandler.signInFromSignUp() }
            )
        }
    }
}
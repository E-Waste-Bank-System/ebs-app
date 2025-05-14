package com.example.ebs.ui.face.starter

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ebs.R
import com.example.ebs.service.AuthResponse
import com.example.ebs.ui.face.components.inputs.AestheticButton
import com.example.ebs.ui.face.components.structures.CenterColumn
import com.example.ebs.ui.face.components.structures.CenterRow
import com.example.ebs.ui.face.components.inputs.InputSpace
import com.example.ebs.ui.face.components.texts.TextContentM
import com.example.ebs.ui.face.components.texts.TextTitleL
import com.example.ebs.ui.face.components.texts.TextTitleS
import com.example.ebs.ui.navigation.NavigationHandler
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navHandler: NavigationHandler,
    signedIn: MutableState<String?>,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
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

        val username = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val notel = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val waitEmail = remember { mutableStateOf(false) }

        InputSpace(stringResource(R.string.username),username)
        InputSpace(stringResource(R.string.email),email)
        InputSpace(stringResource(R.string.notel),notel)
        InputSpace(stringResource(R.string.password),password)

        Spacer(modifier = Modifier.height(16.dp))

        AestheticButton(
            content = {
                if (waitEmail.value == false) {
                    TextTitleS(
                        buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.White)) {
                                append("Daftar")
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Center),
                        mod = true
                    )
                } else {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            },
            onClick = {
                coroutineScope.launch {
                    viewModel.authManagerState.signUpWithEmail(email.value, password.value)
                        .collect { result ->
                            waitEmail.value = false
                            if(result is AuthResponse.Success) {
                                signedIn.value = viewModel.authManagerState.getUserId()
                                Log.e("UserId","${signedIn.value}")
                                navHandler.menuFromSignUp()
                            } else {
                                Log.d("AuthManager", result.toString())
                            }
                        }
                }
            }
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
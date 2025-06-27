package com.example.ebs.ui.screens.starter

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.ebs.R
import com.example.ebs.data.structure.remote.ebs.detections.head.Detection
import com.example.ebs.service.auth.AuthResponse
import com.example.ebs.ui.components.inputs.AestheticButton
import com.example.ebs.ui.components.inputs.InputSpace
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentM
import com.example.ebs.ui.components.texts.TextTitleM
import com.example.ebs.ui.components.texts.TextTitleXL
import com.example.ebs.ui.screens.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    viewModelMain: MainViewModel
) {
    Log.d("Route", "This is SignUp")
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val exitDialogue = remember { mutableStateOf(false) }

    BackHandler { exitDialogue.value = true }

    if (exitDialogue.value) {
        viewModelMain.navHandler.exitDialogue()
        exitDialogue.value = false
    }

    CenterColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        TextTitleXL(stringResource(R.string.sign_up))

        Spacer(modifier = Modifier.height(32.dp))

        val username = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val notel = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val waitEmail = remember { mutableStateOf(false) }

        InputSpace(stringResource(R.string.username),username)
        InputSpace(stringResource(R.string.email),email)
        InputSpace(stringResource(R.string.notel),notel)
        InputSpace(stringResource(R.string.password),password, Modifier.imePadding())

        Spacer(modifier = Modifier.height(16.dp))

        AestheticButton(
            content = {
                if (!waitEmail.value) {
                    TextTitleM(
                        buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.White)) {
                                append("Daftar")
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
            },
            onClick = {
                viewModelMain.localHistory.value = mutableListOf(Detection())
                waitEmail.value = true
                coroutineScope.launch {
                    try {
                        viewModelMain.authManagerState
                            .signUpWithEmail(email.value, password.value)
                            .collect { result ->
                                waitEmail.value = false
                                when (result) {
                                    is AuthResponse.Success -> {
                                        viewModelMain.updateLocalCred(
                                            viewModelMain.authManagerState
                                                .getAuthToken() ?: ""
                                        )
                                        Log.e("UserId", viewModelMain.localCred)
                                        // viewModelAuth.localCred?.let { userPref.saveAuthToken(it) }
                                        viewModelMain.navHandler.menuFromSignUp()
                                    }

                                    else -> {
                                    Toast.makeText(
                                        context,
                                        if (result.toString()
                                                .contains("Unable to resolve host", ignoreCase = true)
                                        )
                                            "Ups?! Tidak ada koneksi internet"
                                        else
                                            result.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                }
                            }
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            if (e.localizedMessage?.contains("Unable to resolve host", ignoreCase = true) == true)
                                "Ups?! Tidak ada koneksi internet"
                            else
                                e.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
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
                modifier = Modifier
                    .clickable { viewModelMain.navHandler.signInFromSignUp() }
            )
        }
    }
}
package com.example.ebs.ui.screens.starter

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ebs.R
import com.example.ebs.data.repositories.UserPreferencesRepository
import com.example.ebs.service.auth.AuthResponse
import com.example.ebs.ui.components.inputs.AestheticButton
import com.example.ebs.ui.components.inputs.InputSpace
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentL
import com.example.ebs.ui.components.texts.TextContentM
import com.example.ebs.ui.components.texts.TextTitleL
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.screens.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    navController: NavController,
    userPref: UserPreferencesRepository,
    viewModelAuth: MainViewModel,
    navigateTo: String?
) {
    val checkIn = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
//        val token = userPref.authToken.firstOrNull()
//        viewModelAuth.updateLocalCred(token ?: "")
//        Log.e("TAG", "OneHalfCredCheck: ${viewModelAuth.localCred.take(10)}")
        checkIn.value = true
    }

    LaunchedEffect(navigateTo) {
        if (navigateTo == "profile") {
            viewModelAuth.navHandler.menuFromSignIn()
        }
    }

    Log.d("Route", "This is SignInScreen")
    if(checkIn.value) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        val exitDialogue = remember { mutableStateOf(false) }

        BackHandler { exitDialogue.value = true }

        if (exitDialogue.value) {
            viewModelAuth.navHandler.exitDialogue()
            exitDialogue.value = false
        }
        CenterColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            TextTitleL(buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(stringResource(R.string.helloSignIn))
                }
                append(stringResource(R.string.temanSignIn))
            })

            Spacer(modifier = Modifier.height(16.dp))

            TextContentL(stringResource(R.string.Intro))
            TextContentL(stringResource(R.string.Intro_n1))
            TextContentL(stringResource(R.string.Intro_n2))

            Spacer(modifier = Modifier.height(64.dp))

            val email = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }
            val waitEmail = remember { mutableStateOf(false) }

            InputSpace(stringResource(R.string.email), email)
            InputSpace(stringResource(R.string.password), password)

            Spacer(modifier = Modifier.height(16.dp))

            AestheticButton(
                content = {
                    if (!waitEmail.value) {
                        TextTitleS(
                            buildAnnotatedString {
                                withStyle(SpanStyle(color = Color.White)) {
                                    append("Login")
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
                    waitEmail.value = true
                    coroutineScope.launch {
                        viewModelAuth.authManagerState.signInWithEmail(email.value, password.value)
                            .collect { result ->
                                waitEmail.value = false
                                if (result is AuthResponse.Success) {
//                                    userPref.saveAuthToken(viewModelAuth.authManagerState.getAuthToken())
                                    viewModelAuth.navHandler.menuFromSignIn()
                                    Log.e("Udah Masuk?", "Ini Udah Masuk? ${viewModelAuth.authManagerState.isSignedIn()}")
                                } else {
                                    Log.e("AuthManager", result.toString())
                                }
                            }
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            CenterRow(
                modifier = Modifier
                    .height(50.dp)
            ) {
                CenterColumn(
                    modifier = Modifier
                        .width(100.dp)
                        .fillMaxHeight()
                ) {
                    HorizontalDivider(color = Color.Gray)
                }
                Text(
                    text = stringResource(R.string.or),
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(start = 5.dp, bottom = 5.dp, end = 5.dp)
                )
                CenterColumn(
                    modifier = Modifier
                        .width(100.dp)
                        .fillMaxHeight()
                ) {
                    HorizontalDivider(color = Color.Gray)
                }
            }

            val wait: MutableState<Boolean> = remember {
                mutableStateOf(false)
            }
            if (wait.value) {
                CenterRow(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }

            Card(
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(60.dp)
                    .clickable {
                        wait.value = true
                        coroutineScope.launch {
                            viewModelAuth.authManagerState.loginGoogleUser(context)
                                .collect { result ->
                                    wait.value = false
                                    if (result is AuthResponse.Success) {
                                        viewModelAuth.navHandler.menuFromSignIn()
                                    } else {
                                        Log.e("AuthManager", result.toString())
                                    }
                                }
                        }
                    }
            ) {
                CenterRow(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    TextTitleS(
                        stringResource(R.string.signInGoogle),
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    Image(
                        painter = painterResource(R.drawable.google),
                        contentDescription = null,
                        modifier = Modifier
                            .height(30.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            CenterRow {
                TextContentM(stringResource(R.string.belumPunyaAkun))
                TextContentM(
                    buildAnnotatedString {
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append(stringResource(R.string.daftar))
                        }
                    },
                    modifier = Modifier
                        .clickable { viewModelAuth.navHandler.signUpFromSignIn() }
                )
            }
        }
    }
}

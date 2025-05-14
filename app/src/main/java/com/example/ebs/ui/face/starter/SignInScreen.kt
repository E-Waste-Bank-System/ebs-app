package com.example.ebs.ui.face.starter

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ebs.R
import com.example.ebs.service.AuthResponse
import com.example.ebs.ui.face.components.inputs.AestheticButton
import com.example.ebs.ui.face.components.inputs.InputSpace
import com.example.ebs.ui.face.components.structures.CenterColumn
import com.example.ebs.ui.face.components.structures.CenterRow
import com.example.ebs.ui.face.components.texts.TextContentL
import com.example.ebs.ui.face.components.texts.TextContentM
import com.example.ebs.ui.face.components.texts.TextTitleL
import com.example.ebs.ui.face.components.texts.TextTitleS
import com.example.ebs.ui.navigation.NavigationHandler
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    navHandler: NavigationHandler,
    signedIn: MutableState<String?>,
    viewModel: SignInViewModel = hiltViewModel()
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
        TextTitleL(buildAnnotatedString {
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(stringResource(R.string.helloSignIn))
            }
            append(stringResource(R.string.temanSignIn))
        }, mod = true)

        Spacer(modifier = Modifier.height(16.dp))

        TextContentL(stringResource(R.string.Intro))
        TextContentL(stringResource(R.string.Intro_n1))
        TextContentL(stringResource(R.string.Intro_n2))

        Spacer(modifier = Modifier.height(64.dp))

        InputSpace(stringResource(R.string.username))
        InputSpace(stringResource(R.string.password))

        Spacer(modifier = Modifier.height(16.dp))

        AestheticButton(
            text = stringResource(R.string.login),
            onClick = {
                signedIn.value = "true"
                navHandler.menuFromSignIn()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        CenterRow(
            modifier = Modifier
                .height(50.dp)
        ){
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

        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        val wait: MutableState<Boolean> = remember {
            mutableStateOf(false)
        }
        if (wait.value) {
            CenterRow(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
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
                        viewModel.authManagerState.loginGoogleUser(context)
                            .collect { result ->
                                wait.value = false
                                if(result is AuthResponse.Success) {
                                    signedIn.value = viewModel.authManagerState.getGoogleProfilePictureUrl()
                                    navHandler.menuFromSignIn()
                                } else {
                                    Log.d("AuthManager", result.toString())
                                }
                            }
                    }
                }
        ) {
            CenterRow (
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ){
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
                mod = true,
                modifier = Modifier
                    .clickable { navHandler.signUpFromSignIn() }
            )
        }
    }
}

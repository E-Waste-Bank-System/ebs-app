package com.example.ebs.ui.face.starter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    CenterColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        TextTitleL("Buat Akun!")

        Spacer(modifier = Modifier.height(32.dp))

        InputSpace("Nama")
        InputSpace("Email")
        InputSpace("No. Telepon")
        InputSpace("Password")

        Spacer(modifier = Modifier.height(16.dp))

        AestheticButton(
            text = "Daftar",
            onClick = { navHandler.menuFromSignUp() }
        )

        Spacer(modifier = Modifier.height(32.dp))

        CenterRow {
            TextContentM("Sudah punya akun? ")
            TextContentM(
                buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append("Login")
                    }
                },
                mod = true,
                modifier = Modifier
                    .clickable { navHandler.signInFromSignUp() }
            )
        }
    }
}
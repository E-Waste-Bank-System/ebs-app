package com.example.ebs.ui.face.components.shapes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.ebs.R
import com.example.ebs.ui.face.components.structures.CenterColumn
import com.example.ebs.ui.navigation.NavigationHandler

@Composable
fun TopBarPage(
    title: Any,
    navHandler: NavigationHandler,
    noPad: Boolean = false,
    customBack: Color = MaterialTheme.colorScheme.background,
    mod: Boolean = false,
    content: @Composable () -> Unit
){
    Scaffold(
        topBar = {
            TopBer(
                title,navHandler,customBack,mod
            )
        },
    ) { padding ->
        CenterColumn(
            vArr = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = if (noPad) PaddingValues(0.dp) else padding)
        ) {
            content()
        }
    }
}
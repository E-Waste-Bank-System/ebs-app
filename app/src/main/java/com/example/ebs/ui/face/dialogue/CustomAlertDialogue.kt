package com.example.ebs.ui.face.dialogue

import android.R.attr.left
import android.R.attr.right
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ebs.ui.face.components.texts.TextContentL
import com.example.ebs.ui.face.components.texts.TextTitleL
import com.example.ebs.ui.face.components.texts.TextTitleM
import com.example.ebs.ui.navigation.NavigationHandler

@Composable
fun CustomAlertDialogue(
    title: String,
    desc: String,
    right: String,
    left: String,
    modifier: Modifier = Modifier,
    rightAct: () -> Unit = {},
    leftAct: () -> Unit = {}
){
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextTitleL(title)
            TextTitleM(
                desc,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.align(Alignment.End)
            ) {
                Button(
                    onClick = { leftAct() }
                ) {
                    Text(left)
                }
                Button(
                    onClick = { rightAct() },
                ) {
                    Text(right)
                }
            }
        }
    }
}
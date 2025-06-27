package com.example.ebs.ui.dialogues.bases

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ebs.ui.components.texts.TextContentM
import com.example.ebs.ui.components.texts.TextTitleM
import com.example.ebs.ui.components.texts.TextTitleS

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
            .fillMaxWidth(0.85f),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(50.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 32.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextTitleM(title)
            TextContentM(
                desc,
                modifier = Modifier.padding(vertical = 20.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    onClick = { leftAct() }
                ) {
                    TextTitleS(left)
                }
                Spacer(modifier = Modifier.padding(horizontal = 12.dp))
                Button(
                    colors = ButtonDefaults.buttonColors().copy(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    onClick = { rightAct() },
                ) {
                    TextTitleS(right)
                }
            }
        }
    }
}
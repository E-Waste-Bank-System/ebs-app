package com.example.ebs.ui.dialogues.bases

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow

@Composable
fun ReminderResult(
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    updateMod: MutableState<Boolean> = mutableStateOf(false),
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .padding(24.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if(isSystemInDarkTheme()) Color(0xFF23425A) else MaterialTheme.colorScheme.surfaceBright
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        CenterColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            content()
            CenterRow(
                hArr = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 5.dp)
            ){
                CenterRow(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 5.dp)
                        .border(
                            BorderStroke(
                                1.dp,
                                if (isSystemInDarkTheme()) Color.White else Color.Black
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            onCancel()
                        }
                ){
                    Text("Batalkan",modifier = Modifier.padding(10.dp))
                }
                if (!updateMod.value) {
                    CenterRow(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 5.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable {
                                onConfirm()
                            }
                    ) {
                        Text("Konfirmasi", modifier = Modifier.padding(10.dp), color = Color.White)
                    }
                }
            }
        }
    }
}
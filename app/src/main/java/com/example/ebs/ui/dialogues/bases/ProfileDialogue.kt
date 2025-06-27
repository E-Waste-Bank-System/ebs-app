package com.example.ebs.ui.dialogues.bases

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ebs.R
import com.example.ebs.ui.components.shapes.MyIcon
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow

@Composable
fun ProfileDialogue(
    title: String,
    @DrawableRes icon: Int,
    navController: NavController,
    content: @Composable () -> Unit
){
    Card {
        CenterColumn(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 20.dp)
        ) {
            CenterRow(
                hArr = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                CenterRow(
                    hArr = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                ) {
                    Text(text = title)
                    MyIcon(
                        painterResource(id = icon),
                        contentDescription = "avItem.name",
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                MyIcon(
                    painterResource(id = R.drawable.close),
                    contentDescription = "avItem.name",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
            }
            content()
        }
    }
}
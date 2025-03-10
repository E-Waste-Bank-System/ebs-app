package com.example.ebs.ui.face.dialogue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ebs.R
import com.example.ebs.ui.face.components.structures.CenterColumn
import com.example.ebs.ui.face.components.structures.CenterRow
import com.example.ebs.ui.face.components.shapes.MyCard
import com.example.ebs.ui.face.components.shapes.MyIcon
import com.example.ebs.ui.face.components.shapes.MyIconImage

@Composable
fun Feedback() {
    Card{
        CenterColumn(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 20.dp)
                .width(400.dp)
        ){
            CenterRow(
                hArr = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ){
                MyIcon(
                    painterResource(id = R.drawable.close),
                    contentDescription = "avItem.name"
                )
            }
            Text(text = "Puas nggak pake aplikasi e-waste kita?")
            Row{
                MyCard{
                    Row{
                        MyIconImage(
                            painterResource(id = R.drawable.clock),
                            contentDescription = "avItem.name"
                        )
                        Text(text = "Sangat puas")
                    }
                }
                MyCard{
                    Row{
                        MyIconImage(
                            painterResource(id = R.drawable.turtle),
                            contentDescription = "avItem.name"
                        )
                        Text(text = "Sangat puas")
                    }
                }
            }
            Row{
                MyCard{
                    Row{
                        MyIconImage(
                            painterResource(id = R.drawable.laptop),
                            contentDescription = "avItem.name"
                        )
                        Text(text = "Sangat puas")
                    }
                }
                MyCard{
                    Row{
                        MyIconImage(
                            painterResource(id = R.drawable.smile),
                            contentDescription = "avItem.name"
                        )
                        Text(text = "Sangat puas")
                    }
                }
            }
            Row{
                MyCard{
                    Row{
                        MyIconImage(
                            painterResource(id = R.drawable.clock),
                            contentDescription = "avItem.name"
                        )
                        Text(text = "Sangat puas")
                    }
                }
                MyCard{
                    Row{
                        MyIconImage(
                            painterResource(id = R.drawable.clock),
                            contentDescription = "avItem.name"
                        )
                        Text(text = "Sangat puas")
                    }
                }
            }
            Button(onClick = {}) {
                Text(text = "Konfirmasi")
            }
        }
    }
}
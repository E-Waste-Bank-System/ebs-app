package com.example.ebs.ui.dialogues

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ebs.R
import com.example.ebs.ui.components.shapes.MyCard
import com.example.ebs.ui.components.shapes.MyIcon
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentM
import com.example.ebs.ui.components.texts.TextContentS


@Composable
fun ApplyRequest(navController: NavController) {
    Card{
        CenterColumn(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 20.dp)
                .width(400.dp)
                .height(500.dp)
        ){
            CenterRow(
                hArr = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ){
                CenterRow(
                    hArr = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                ) {
                    Text(text = "Smartphone")
                    MyIcon(
                        painterResource(id = R.drawable.recycle),
                        contentDescription = "avItem.name"
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
            MyCard(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                CenterRow(
                    hArr = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ){
                    CenterColumn{
                        TextContentM(text = "Kategori")
                        TextContentM(text = "Jenis")
                    }
                    CenterColumn{
                        TextContentM(text = "Jumlah")
                        TextContentM(text = "1")
                    }
                    CenterColumn{
                        TextContentM(text = "Estimasi Harga")
                        TextContentM(text = "Rp. 100.000")
                    }
                }
            }
            MyCard {
                CenterColumn(
                    hAli = Alignment.Start,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ){
                    TextContentS(text = "Pilih Jadwal Jemput (Setiap hari minggu)")
                    Row {
                        MyCard{
                            CenterColumn(
                                modifier = Modifier
                                    .padding(7.dp)
                            ){
                                TextContentS(text = "Minggu")
                                TextContentS(text = "5")
                                TextContentS(text = "Jan")
                            }
                        }
                        MyCard(
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                        ){
                            CenterColumn(
                                modifier = Modifier
                                    .padding(7.dp)
                            ){
                                TextContentS(text = "Minggu")
                                TextContentS(text = "5")
                                TextContentS(text = "Jan")
                            }
                        }
                        MyCard(
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                        ){
                            CenterColumn(
                                modifier = Modifier
                                    .padding(7.dp)
                            ){
                                TextContentS(text = "Minggu")
                                TextContentS(text = "5")
                                TextContentS(text = "Jan")
                            }
                        }
                        MyCard{
                            CenterColumn(
                                modifier = Modifier
                                    .padding(7.dp)
                            ){
                                TextContentS(text = "Kategori")
                                TextContentS(text = "Jenis")
                            }
                        }
                    }
                    Row {
                        MyCard(
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                        ){
                            TextContentS(
                                text = "Kategori",
                                modifier = Modifier
                                    .padding(7.dp)
                            )
                        }
                        MyCard(
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                        ){
                            TextContentS(
                                text = "Kategori",
                                modifier = Modifier
                                    .padding(7.dp)
                            )
                        }
                        MyCard(
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                        ){
                            TextContentS(
                                text = "Kategori",
                                modifier = Modifier
                                    .padding(7.dp)
                            )
                        }
                    }
                }
            }
            MyCard {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .fillMaxSize(0.5f)
                ) {
                    TextContentM(text="Catatan:")
                    TextContentS(
                        text="Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum",
                    )
                }
            }
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(text = "Konfirmasi")
            }
        }
    }
}


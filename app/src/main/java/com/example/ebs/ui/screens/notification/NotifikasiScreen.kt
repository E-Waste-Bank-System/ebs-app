package com.example.ebs.ui.screens.notification

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ebs.ui.components.shapes.Indicator
import com.example.ebs.ui.components.shapes.TopBarPage
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentS
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.screens.AuthViewModel

@Composable
fun NotifikasiScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: NotifikasiViewModel = hiltViewModel(),
    viewModelAuth: AuthViewModel = hiltViewModel(),
) {
    viewModelAuth.initializeNavHandler(navController)
    Log.d("Route", "This is Notifikasi")
    TopBarPage("Notifikasi",viewModelAuth.navHandler) {
        LazyColumn {
            items(1) {
                CenterRow(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(0.85f)
                ) {
                    HorizontalDivider(color = Color.Gray)
                }
                CardNotification(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                ){
                    CenterColumn(
                        hAli = Alignment.Start,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 20.dp)
                    ) {
                        TextTitleS("EcoAdmin")
                        TextContentS("Pengajuan sampah elektronik anda telah diterima! sampah anda akan segera diverifikasi oleh tim kami.")
                    }
                }
            }
        }
        CenterRow(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(0.85f)
        ) {
            HorizontalDivider(color = Color.Gray)
        }
        CardNotification(
            modifier = Modifier
                .fillMaxWidth(0.85f)
        ){
            CenterColumn(
                hAli = Alignment.Start,
                modifier = Modifier
                    .padding(start = 10.dp, end = 20.dp)
            ) {
                TextTitleS("EcoAdmin")
                TextContentS("Sampah elektronik anda telah diverifikasi. Hasil Deteksi:")
                CardNotifikasi2(
                    modifier = Modifier
                        .height(125.dp)
                        .width(180.dp)
                ){
                    CenterColumn(
                        vArr = Arrangement.SpaceBetween,
                        hAli = Alignment.Start,
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        Column {
                            TextContentS("Kategori: Smarphone")
                            TextContentS("Jumlah: 1")
                            TextContentS("Prediksi Harga: Rp. 50.000")
                        }
                        TextContentS(buildAnnotatedString {
                            withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                                append("Catatan: tidak bisa menyala")
                            }
                        }, mod = true)
                        Indicator("Pending", Color(0xFFE27700))
                    }
                }
            }
        }
    }
}


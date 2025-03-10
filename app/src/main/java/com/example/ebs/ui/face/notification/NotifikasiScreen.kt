package com.example.ebs.ui.face.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ebs.R
import com.example.ebs.ui.face.components.structures.CenterColumn
import com.example.ebs.ui.face.components.structures.CenterRow
import com.example.ebs.ui.face.components.shapes.Indicator
import com.example.ebs.ui.face.components.texts.TextContentS
import com.example.ebs.ui.face.components.texts.TextTitleS
import com.example.ebs.ui.face.components.shapes.TopBarPage
import com.example.ebs.ui.navigation.NavigationHandler

@Composable
fun NotifikasiScreen(
    navHandler: NavigationHandler,
    modifier: Modifier = Modifier,
    viewModel: NotifikasiViewModel = hiltViewModel()
) {
    TopBarPage("Notifikasi") {
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

@Composable
fun CardNotification(
    modifier: Modifier = Modifier,
    icon: Int = R.drawable.recycle,
    content: @Composable () -> Unit
){
    CenterRow(
        vAli = Alignment.Top,
        modifier = modifier
            .padding(vertical = 10.dp)
    ){
        Box(
            contentAlignment = Alignment.Center
        ) {
            CenterRow(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            ){
                Icon(
                    painterResource(icon),
                    contentDescription = "Notification",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            if(true) {
                CenterRow(
                    modifier = Modifier
                        .absoluteOffset(x = 13.dp,y = 25.dp)
                        .padding(bottom = 20.dp)
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Color.Green.copy(
                            green = 0.7f
                        ))
                ) {}
            }
        }
        content()
    }
}

@Composable
fun CardNotifikasi2(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
){
    Card (
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(5.dp)
    ){
        CenterRow(
            hArr = Arrangement.Start,
            modifier = modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(15.dp)
        ) {
            content()
        }
    }
}
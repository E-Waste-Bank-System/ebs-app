package com.example.ebs.ui.screens.detail

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.ebs.R
import com.example.ebs.data.structure.remote.ebs.detections.DataDetections
import com.example.ebs.data.structure.remote.ebs.detections.Detection
import com.example.ebs.ui.components.gradients.getGredienButton
import com.example.ebs.ui.components.shapes.TopBarPage
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentL
import com.example.ebs.ui.components.texts.TextContentM
import com.example.ebs.ui.components.texts.TextTitleM
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.dialogues.ReminderResult
import com.example.ebs.ui.screens.MainViewModel
import java.text.NumberFormat

data class ScanResult(
    val category: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val riskLvl: Int = 0,
    val regressionResult: Int = 0,
    val suggestion: List<String> = emptyList(),
    val total: Int = 0
)

@Composable
fun WasteDetailScreen(
    navController: NavController,
    data: DataDetections = DataDetections(),
    viewModelAuth: MainViewModel,
    viewModel: WasteDetailViewModel = hiltViewModel()
) {
    Log.d("Route", "This is Result")

    val reminder = rememberSaveable { mutableStateOf(false) }

    val listScan by remember { mutableStateOf(data.predictions.groupBy { it.category }.map {
        val first = it.value.firstOrNull()
        ScanResult(
            category = first?.category ?: "",
            description = first?.description ?: "",
            imageUrl = first?.imageUrl ?: "",
            riskLvl = first?.riskLvl ?: 0,
            regressionResult = first?.regressionResult?.toInt() ?: 0,
            suggestion = first?.suggestion ?: emptyList(),
            total = it.value.size
        )
    }) }

    TopBarPage(
        "Result",
        viewModelAuth.navHandler
    ){
        if(data.predictions.first() == Detection(
                id = "",
                imageUrl = "",
                category = "MANA E-WASTENYA",
                confidence = 0.0,
                regressionResult = null,
                description = "Ketika aplikasi menerima gambar yang tidak dapat dikenali, maka sumber daya yang digunakan menjadi sia-sia",
                suggestion = listOf("Hmm... Sepertinya Bukan E-Waste \uD83E\uDD14","Semoga gambar yang diunggah sesuai dengan kategori e-waste yang ada","Jika tidak, silakan unggah gambar lain yang sesuai"),
                riskLvl = 5,
                detectionSource = ""
            )) {} else {
            LazyColumn {
                items(
                    listScan.size,
                    key = { hist -> listScan[hist].description },
                ) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        CenterColumn(
                            hAli = Alignment.Start,
                            modifier = Modifier
                                .align(TopCenter)
                                .fillMaxWidth(0.85f)
                        ) {
                            CenterColumn(
                                hAli = Alignment.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentSize()
                                    .border(
                                        BorderStroke(
                                            1.dp,
                                            getGredienButton(Color(0xFFD3A6E0), Color(0xFF10A2D1))
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                if (listScan[item].imageUrl == "") {
                                    Image(
                                        painterResource(R.drawable.nopicture),
                                        contentDescription = "Icon",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(100.dp)
                                            .align(Alignment.CenterHorizontally)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                } else {
                                    Image(
                                        painter = rememberAsyncImagePainter(listScan[item].imageUrl),
                                        contentDescription = "Account Image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .padding(bottom = 25.dp, top = 15.dp)
                                            .width(300.dp)
                                            .height(200.dp)
                                            .align(Alignment.CenterHorizontally)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                }
                            }
                            TextTitleM(listScan[item].category)
                            TextContentL(
                                listScan[item].description,
                                textAlign = TextAlign.Start
                            )
                            Spacer(Modifier.padding(5.dp))
                            CenterRow {
                                TextTitleS("Saran dan Tindakan dari AI")
                                Image(
                                    painter = painterResource(R.drawable.ai),
                                    contentDescription = "ai",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .padding(5.dp)
                                )
                            }
                            listScan[item].suggestion.forEach { suggestionItem ->
                                CenterRow {
                                    Box(
                                        modifier = Modifier
                                            .padding(7.5.dp)
                                            .size(5.dp)
                                            .background(
                                                Color.Gray,
                                                shape = CircleShape
                                            )
                                            .align(Top)
                                    )
                                    TextContentM(
                                        "$suggestionItem",
                                        modifier = Modifier
                                            .align(Top),
                                        textAlign = TextAlign.Start
                                    )
                                }
                            }
                            Spacer(Modifier.padding(10.dp))
                            CenterColumn(
                                hAli = Alignment.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                                    .border(
                                        BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                TextTitleS(
                                    "Prediksi Kerusakan E-waste",
                                    modifier = Modifier.padding(10.dp)
                                )
                                LinearProgressIndicator(
                                    progress = {
                                        listScan[item].riskLvl.toFloat() / 10
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp)
                                        .padding(horizontal = 10.dp),
                                    color = MaterialTheme.colorScheme.primary,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                                CenterRow(
                                    hArr = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                ) {
                                    TextContentM("Rendah")
                                    TextContentM("Tinggi")
                                }
                            }
                            Spacer(Modifier.padding(10.dp))
                            CenterColumn(
                                hAli = Alignment.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentSize()
                                    .border(
                                        BorderStroke(
                                            1.dp,
                                            getGredienButton(Color(0xFFD3A6E0), Color(0xFF10A2D1))
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                TextTitleS(
                                    "Total Estimasi Harga",
                                    modifier = Modifier.padding(10.dp)
                                )
                                CenterRow(
                                    hArr = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                ) {
                                    TextContentL(listScan[item].category)
                                    TextContentL(listScan[item].total.toString())
                                    TextContentL(
                                        "Rp. " + NumberFormat.getInstance()
                                            .format(listScan[item].regressionResult * listScan[item].total)
                                    )
                                }
                            }
                            Spacer(Modifier.padding(50.dp))
                        }
                    }
                }
            }
        }
    }
    if (!reminder.value) {
        if(data.predictions.first() == Detection(
                id = "",
                imageUrl = "",
                category = "MANA E-WASTENYA",
                confidence = 0.0,
                regressionResult = null,
                description = "Ketika aplikasi menerima gambar yang tidak dapat dikenali, maka sumber daya yang digunakan menjadi sia-sia",
                suggestion = listOf("Hmm... Sepertinya Bukan E-Waste \uD83E\uDD14","Semoga gambar yang diunggah sesuai dengan kategori e-waste yang ada","Jika tidak, silakan unggah gambar lain yang sesuai"),
                riskLvl = 5,
                detectionSource = ""
        )){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable {
                        reminder.value = !reminder.value
                    }
            ){
                ReminderResult(
                    onCancel = {
                        viewModelAuth.navHandler.back()
                    },
                    onConfirm = {
                        viewModelAuth.navHandler.scanFromDetail(data)
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.95f)
                        .clip(RoundedCornerShape(8.dp))
                ){
                    CenterRow (
                        modifier = Modifier
                            .padding(start = 15.dp, end = 15.dp, top = 10.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSystemInDarkTheme()) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.surface)
                            .padding(10.dp)
                    ){
                        CenterColumn (
                            hAli = Alignment.Start,
                            vArr = Arrangement.Top,
                            modifier = Modifier
                                .align(Top)
                                .padding(start = 5.dp)
                        ){
                            TextTitleS(
                                text = "Hmm... Sepertinya Bukan E-Waste \uD83E\uDD14",
                                textAlign = TextAlign.Start
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            TextContentM(
                                text = "Kami hanya mendeteksi sampah elektronik seperti kabel, baterai, dan komponen kecil.\n" +
                                        "Yuk coba unggah e-waste yang sesuai! \uD83D\uDE0F",
                                modifier = Modifier.padding(bottom = 8.dp),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable {
                        reminder.value = !reminder.value
                    }
            ) {
                ReminderResult(
                    onCancel = {
                        viewModelAuth.navHandler.back()
                    },
                    onConfirm = {
                        reminder.value = !reminder.value
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.95f)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    CenterRow(
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSystemInDarkTheme()) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.surface)
                            .padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.star),
                            contentDescription = "star",
                            modifier = Modifier
                                .padding(5.dp)
                                .size(30.dp)
                                .align(Top),
                            contentScale = ContentScale.Crop
                        )
                        CenterColumn(
                            hAli = Alignment.Start,
                            vArr = Arrangement.Top,
                            modifier = Modifier
                                .align(Top)
                        ) {
                            TextTitleS(text = "Catatan:")
                            TextContentM(
                                text = "Estimasi harga dan saran berbasis AI dapat berubah tergantung kondisi fisik perangkat dan modelnya. Untuk hasil maksimal, simpan perangkat dalam keadaan utuh saat disetor.",
                                modifier = Modifier.padding(bottom = 8.dp),
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }
    }
}
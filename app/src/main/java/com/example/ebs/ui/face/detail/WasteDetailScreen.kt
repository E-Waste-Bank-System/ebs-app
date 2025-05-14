package com.example.ebs.ui.face.detail

import android.widget.ProgressBar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
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
import com.example.ebs.ui.face.components.texts.TextContentM
import com.example.ebs.ui.face.components.texts.TextTitleL
import com.example.ebs.ui.face.components.texts.TextTitleM
import com.example.ebs.ui.face.dialogue.ReminderResult
import com.example.ebs.ui.face.history.ResultScreen
import com.example.ebs.ui.face.notification.CardNotification
import com.example.ebs.ui.face.notification.CardNotifikasi2
import com.example.ebs.ui.navigation.NavigationHandler


@Composable
fun WasteDetailScreen(
    navHandler: NavigationHandler,
    barang: String,
    viewModel: WasteDetailViewModel = hiltViewModel()
) {
    val reminder = remember { mutableStateOf(false) }
    TopBarPage("Result",navHandler){
        Box (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            CenterColumn(
                hAli = Alignment.Start,
                modifier = Modifier
                    .align(TopCenter)
                    .fillMaxWidth(0.85f)
            ) {
                TextTitleS("Smartphone Bekas")
                TextContentM("Smartphone bekas mengandung berbagai komponen berharga seperti emas, perak, tembaga, dan litium. Namun, jika tidak dibuang dengan benar, bahan kimia dari baterainya bisa merusak lingkungan. Smartphone juga menyumbang e-waste dalam jumlah besar setiap tahun.")
                Spacer(Modifier.padding(10.dp))
                CenterRow {
                    TextTitleS("Saran dan Tindakan dari AI")
                    Image(painter = painterResource(R.drawable.ai), contentDescription = "ai", modifier = Modifier.padding(5.dp))
                }
                TextContentM("Nilai Jual Tinggi: Jika masih menyala, pertimbangkan untuk dijual ulang atau disumbangkan. Smartphone bekas memiliki nilai ekonomis tinggi dibanding e-waste lainnya.\n" +
                        "Hapus Data: Pastikan semua data pribadi dihapus sebelum menyetor smartphone ke bank sampah elektronik.\n" +
                        "Waspadai Baterai: Jangan membongkar baterai sendiri. Baterai lithium-ion mudah terbakar jika rusak atau tertusuk.\n" +
                        "Wawasan AI: Smartphone keluaran 3–5 tahun terakhir masih memiliki komponen logam mulia yang bernilai tinggi jika didaur ulang dengan benar.")
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
                ){
                    TextTitleS("Prediksi Risiko Terhadap Lingkungan",modifier = Modifier.padding(10.dp))
                    LinearProgressIndicator(
                        progress = {
                            0.6f
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .padding(horizontal = 10.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor =  MaterialTheme.colorScheme.surfaceVariant
                    )
                    CenterRow (
                        hArr = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ){
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
                            BorderStroke(1.dp, Color.Black),
                            shape = RoundedCornerShape(8.dp)
                        )
                ){
                    TextTitleS("Total Estimasi Harga",modifier = Modifier.padding(10.dp))
                    CenterRow (
                        hArr = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ){
                        TextContentM("Smartphone")
                        TextContentM("x2")
                        TextContentM("Rp100.000")
                    }
                    CenterRow (
                        hArr = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ){
                        TextContentM("Laptop")
                        TextContentM("x2")
                        TextContentM("Rp100.000")
                    }
                    CenterRow (
                        hArr = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ){
                        TextContentM("Powerbank")
                        TextContentM("x2")
                        TextContentM("Rp100.000")
                    }
                    CenterRow (
                        hArr = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ){
                        TextTitleS("Rendah")
                        TextTitleS("Tinggi")
                    }
                }
                Spacer(Modifier.padding(50.dp))
            }
            if(!reminder.value) {
                ReminderResult(
                    reminder,
                    navHandler,
                    Modifier
                        .align(TopCenter)
                        .padding(
                            top = with(LocalDensity.current) { (LocalWindowInfo.current.containerSize.height * 0.5f).toDp() }
                        )
                )
            }
        }
    }
}
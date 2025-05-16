package com.example.ebs.ui.screens.detail

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ebs.R
import com.example.ebs.ui.components.shapes.TopBarPage
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentM
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.screens.AuthViewModel
import com.example.ebs.ui.dialogues.ReminderResult


@Composable
fun WasteDetailScreen(
    navController: NavController,
    barang: String,
    viewModel: WasteDetailViewModel = hiltViewModel(),
    viewModelAuth: AuthViewModel = hiltViewModel()
) {
    viewModelAuth.initializeNavHandler(navController)
    Log.d("Route", "This is Result")
    val reminder = rememberSaveable { mutableStateOf(false) }
    TopBarPage("Result", viewModelAuth.navHandler){
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
                    viewModelAuth.navHandler,
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
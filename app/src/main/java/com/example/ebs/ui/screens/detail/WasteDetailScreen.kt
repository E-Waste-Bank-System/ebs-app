package com.example.ebs.ui.screens.detail

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.ebs.R
import com.example.ebs.data.structure.remote.ebs.detections.Waste
import com.example.ebs.data.structure.remote.ebs.detections.head.Detection
import com.example.ebs.data.structure.remote.ebs.detections.head.ScanResponse
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
import kotlinx.coroutines.delay
import java.text.NumberFormat
import kotlin.math.round

@Composable
fun WasteDetailScreen(
    scanRes: ScanResponse = ScanResponse(),
    img: String? = "",
    viewModelAuth: MainViewModel,
    viewModel: WasteDetailViewModel = hiltViewModel()
) {
    Log.d("Route", "This is Result")
    var check by remember { mutableStateOf(false) }
    var detection by remember { mutableStateOf(Detection()) }
    var scrollToLastItem by remember { mutableStateOf(false) }
    var delayPoll:Long = 40000
    var progressor by remember { mutableFloatStateOf(0.0f) }
    var total  by remember { mutableFloatStateOf(delayPoll.toFloat()) }

    LaunchedEffect(Unit) {
        while (detection.status != "completed") {
            detection = viewModelAuth.pollResult(scanRes.id)
            if( progressor <= (delayPoll * 3 - 10000)) {
                while(progressor < total + 15000) {
                    delay(50)
                    progressor += 75
                }
                Log.e("Progress", "Progress: $progressor, Total: $total, Delay: $delayPoll")
                total += delayPoll
            }
            delay(delayPoll)
            delayPoll -= if (delayPoll >= 10000) round(delayPoll.toDouble() / 2).toLong() else 10000
        }
    }

    LaunchedEffect(detection.status) {
        if (detection.status == "completed") {
            while (progressor < delayPoll * 3) {
                delay(10)
                progressor += 1000
            }
            check = true
        }
    }

    if (!check){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = !check,
                exit = fadeOut(tween(durationMillis = 1000, delayMillis = 0))
            ) {
                LottieAnimation(
                    composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.amine)).value,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(250.dp),
                )
                CircularProgressIndicator(
                    progress = { progressor/delayPoll/3 },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(250.dp),
                    trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
                )
            }
        }
    }

    if(check) {
        val listScan = detection.objects.groupBy { it.category }.map {
            val first = it.value.firstOrNull()
            Waste(
                id = first?.id ?: Waste().id,
                category = first?.category ?: Waste().category,
                description = first?.description ?: Waste().description.toString(),
                riskLvl = first?.riskLvl ?: 0,
                estValue = first?.estValue ?: 0.0,
                suggestions = first?.suggestions ?: emptyList(),
                total = it.value.size
            )
        }

        val slideReminder = remember { mutableStateOf( listScan.size > 1) }
        val reminder = rememberSaveable { mutableStateOf(false) }
        val listState = rememberLazyListState()
        val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

        LaunchedEffect(scrollToLastItem) {
            if (scrollToLastItem) {
                val lastIndex = listScan.size - 1
                if (lastIndex >= 0) {
                    listState.animateScrollToItem(lastIndex)
                }
                scrollToLastItem = false
            }
        }

        TopBarPage("Result", viewModelAuth.navHandler){
            LazyRow(
                state = listState,
                flingBehavior = flingBehavior
            ) {
                items(
                    listScan.size,
                    key = { item -> listScan[item].id },
                ) { item ->
                    CenterRow(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Spacer(modifier = Modifier.fillParentMaxWidth(0.075f))
                        CenterColumn(
                            hAli = Alignment.Start,
                            modifier = Modifier
                                .fillParentMaxWidth(0.85f)
                                .verticalScroll(
                                    rememberScrollState()
                                )
                        ) {
                            CenterColumn(
                                hAli = Alignment.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentSize()
                                    .padding(bottom = 15.dp)
                                    .border(
                                        BorderStroke(
                                            2.dp,
                                            getGredienButton(
                                                Color(0xFFD3A6E0),
                                                Color(0xFF10A2D1)
                                            )
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                if (img == "") {
                                    Image(
                                        painterResource(R.drawable.nopicture),
                                        contentDescription = "Icon",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .padding(15.dp)
                                            .width(300.dp)
                                            .height(200.dp)
                                            .align(Alignment.CenterHorizontally)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                } else {
                                    Image(
                                        painter = rememberAsyncImagePainter(img),
                                        contentDescription = "Account Image",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .padding(15.dp)
                                            .width(300.dp)
                                            .height(200.dp)
                                            .align(Alignment.CenterHorizontally)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                }
                            }
                            TextTitleM(
                                listScan[item].category,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                            TextContentM(
                                listScan[item].description ?: Waste().description.toString(),
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
                            listScan[item].suggestions?.forEach { suggestionItem ->
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
                                        suggestionItem,
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
                                            getGredienButton(
                                                Color(0xFFD3A6E0),
                                                Color(0xFF10A2D1)
                                            )
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
                                            .format(listScan[item].estValue) + ",-",
                                    )
                                }
                            }
                            Spacer(Modifier.padding(50.dp))
                        }
                        Spacer(modifier = Modifier.fillParentMaxWidth(0.075f))
                    }
                }
            }
        }
        if (slideReminder.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        slideReminder.value = !slideReminder.value
                        // Scroll to the last item in the LazyRow
                        val lastIndex = listScan.size - 1
                        if (lastIndex >= 0) {
                            slideReminder.value = false
                            scrollToLastItem = true
                        }
                    }
            ) {
                val infiniteTransition = rememberInfiniteTransition(label = "slide")
                val offsetX by infiniteTransition.animateFloat(
                    initialValue = -20f,
                    targetValue = 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 500, easing = LinearOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ), label = "slideX"
                )

                Icon(
                    painter = painterResource(R.drawable.chevron_left),
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                        .offset { IntOffset(offsetX.dp.roundToPx(), 0) }
                        .align(CenterEnd)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                )
            }
        }
        if (!reminder.value) {
            if(detection.objects.isEmpty()){
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
                            viewModelAuth.navHandler.scanFromDetail(ScanResponse(),"fromDetail")
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
}
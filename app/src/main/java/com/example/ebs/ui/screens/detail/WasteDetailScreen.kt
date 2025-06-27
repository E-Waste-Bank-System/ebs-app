package com.example.ebs.ui.screens.detail

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.ebs.R
import com.example.ebs.data.structure.remote.ebs.detections.Waste
import com.example.ebs.data.structure.remote.ebs.detections.head.Detection
import com.example.ebs.data.structure.remote.ebs.detections.head.ScanResponse
import com.example.ebs.service.PollingForegroundService
import com.example.ebs.ui.components.gradients.getGredienButton
import com.example.ebs.ui.components.shapes.TopBarPage
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentM
import com.example.ebs.ui.components.texts.TextTitleM
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.dialogues.NotEwaste
import com.example.ebs.ui.dialogues.ReminderScanResult
import com.example.ebs.ui.screens.MainViewModel
import com.example.ebs.ui.screens.detail.components.LoadingWasteDetail
import com.example.ebs.ui.screens.detail.components.SlideReminder
import com.example.ebs.utils.cropImage
import kotlinx.coroutines.delay
import java.text.NumberFormat

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun WasteDetailScreen(
    scanRes: ScanResponse = ScanResponse(),
    img: String? = "",
    viewModelMain: MainViewModel,
    fromScan: Boolean = false,
    viewModel: WasteDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var check by remember { mutableStateOf(false) }
    var detection by remember { mutableStateOf(Detection()) }
    val delayPoll:Long = 8000
    val finishLine = delayPoll * 3
    var progressor by remember { mutableFloatStateOf(0.0f) }
    var total  by remember { mutableFloatStateOf(delayPoll.toFloat()) }
    val wait: MutableState<Boolean> = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (detection.status != "completed") {
            try {
                detection = viewModelMain.pollResult(scanRes.id)
                if (progressor <= finishLine) {
                    while (progressor < total - delayPoll / 2) {
                        progressor += delayPoll / 75
                        delay(delayPoll / 300)
                    }
                    while (progressor < total) {
                        progressor += delayPoll / 125
                        delay(delayPoll / 150)
                    }
                    // Optionally update notification here
                    if (total >= finishLine - delayPoll) {
                        total += finishLine - total - delayPoll * 0.2f
                    } else {
                        total += delayPoll
                    }
                }
            } catch (e: Exception) {
                if (e.localizedMessage?.contains("coroutine scope left", ignoreCase = true) != true) {
                    Toast.makeText(
                        context,
                        if (e.localizedMessage
                            ?.contains("Unable to resolve host", ignoreCase = true) == true)
                            "Ups?! Tidak ada koneksi internet"
                        else
                            e.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    LaunchedEffect(detection.status) {
        if (detection.status == "completed" && progressor > delayPoll) {
            while (progressor < finishLine) {
                delay(10)
                progressor += 1000
            }
            check = true
        } else if (detection.status == "completed" && progressor < delayPoll){
            while (progressor < finishLine) {
                delay(1)
                progressor += 400
            }
            check = true
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            if (detection.status != "completed") {
                Log.e("Scans", "DisposableEffect called")
                val intent = Intent(context, PollingForegroundService::class.java).apply {
                    putExtra("scanId", scanRes.id)
                    putExtra("status", detection.status)
                }
                ContextCompat.startForegroundService(context, intent)
            }
        }
    }

    if (!check || detection.status != "completed"){
        LoadingWasteDetail(
            check, progressor, finishLine, detection, scanRes, context, viewModelMain, fromScan
        )
    }


    Log.d("Route", "This is Result")
    if(check) {
        val listScan = detection.objects.map {
            Waste(
                id = it.id,
                category = it.category,
                description = it.description ?: Waste().description.toString(),
                riskLvl = it.riskLvl,
                estValue = it.estValue ?: 0.0,
                boundingBox = it.boundingBox,
                suggestions = it.suggestions ?: emptyList()
            )
        }


        val scrollToLastItem = remember { mutableStateOf(false) }
        val slideReminder = remember { mutableStateOf( listScan.size > 1) }
        val reminder = rememberSaveable { mutableStateOf(false) }
        val listState = rememberLazyListState()
        val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
        val constraint = listOf(
            1110,
            1480
        )
        val imageRequest = ImageRequest.Builder(context)
            .data(img)
            .size(Size.ORIGINAL)
            .crossfade(true)
            .build()
        val ukuranGambar = remember { mutableIntStateOf(0) }
        val painter = rememberAsyncImagePainter(model = imageRequest)
        val state = painter.state

        LaunchedEffect(scrollToLastItem.value) {
            if (scrollToLastItem.value) {
                val lastIndex = listScan.size - 1
                if (lastIndex >= 0) {
                    listState.animateScrollToItem(lastIndex)
                }
                scrollToLastItem.value = false
            }
        }

        TopBarPage("Result", viewModelMain.navHandler){
            LazyRow(
                state = listState,
                flingBehavior = flingBehavior,
                modifier = Modifier
                    .fillMaxSize()
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
                            var widthPx by remember { mutableStateOf(0) }
                            var heightPx by remember { mutableStateOf(0) }
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
                                    .onGloballyPositioned { coordinates ->
                                        widthPx = coordinates.size.width
                                        heightPx = coordinates.size.height
                                    }
                            ) {
                                if (img == "") {
                                    Image(
                                        painterResource(R.drawable.nopicture),
                                        contentDescription = "Icon",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .padding(15.dp)
                                            .width(300.dp)
                                            .align(Alignment.CenterHorizontally)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                } else {
                                    if (wait.value) {
                                        CenterRow(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = 40.dp)
                                        ) {
                                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                    if (state is AsyncImagePainter.State.Success) {
                                        val originalBitmap = (state.result.drawable as? BitmapDrawable)?.bitmap
                                        if (originalBitmap != null) {
                                            val resultCrop = cropImage(
                                                floatArrayOf(
                                                    listScan[item].boundingBox.x.toFloat(),
                                                    listScan[item].boundingBox.y.toFloat(),
                                                    listScan[item].boundingBox.width.toFloat(),
                                                    listScan[item].boundingBox.height.toFloat()
                                                ),
                                                originalBitmap,
                                                ukuranGambar.intValue,
                                                constraint
                                            )
                                            val croppedBitmap = remember(originalBitmap) {
                                                resultCrop?.bitMap
                                            }
                                            if (resultCrop != null) {
                                                ukuranGambar.intValue = resultCrop.size
                                            }
                                            val imageBitmap = croppedBitmap?.asImageBitmap()
                                            if (imageBitmap != null) {
                                                wait.value = false
                                                Image(
                                                    bitmap = imageBitmap,
                                                    contentDescription = "Cropped Image",
                                                    modifier = Modifier
                                                        .padding(15.dp)
                                                        .sizeIn(
                                                            maxWidth = 300.dp,
                                                            maxHeight = 600.dp
                                                        )
                                                        .align(Alignment.CenterHorizontally)
                                                        .clip(RoundedCornerShape(8.dp))
                                                )
                                            } else {
                                                wait.value = false
                                                Image(
                                                    painter = rememberAsyncImagePainter(img),
                                                    contentDescription = "Image",
                                                    modifier = Modifier
                                                        .padding(15.dp)
                                                        .sizeIn(
                                                            maxWidth = 300.dp,
                                                            maxHeight = 600.dp
                                                        )
                                                        .align(Alignment.CenterHorizontally)
                                                        .clip(RoundedCornerShape(8.dp))
                                                )
                                            }
                                        }
                                    } else {
                                        Image(
                                            painter = painter,
                                            contentDescription = "Image",
                                            modifier = Modifier
                                                .padding(15.dp)
                                                .size(ukuranGambar.intValue.dp)
                                                .align(Alignment.CenterHorizontally)
                                                .clip(RoundedCornerShape(8.dp))
                                        )
                                    }
                                }
                            }
                            TextTitleM(
                                listScan[item].category,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Start
                            )
                            Spacer(Modifier.padding(2.dp))
                            TextContentM(
                                listScan[item].description ?: Waste().description.toString(),
                                textAlign = TextAlign.Start
                            )
                            Spacer(Modifier.padding(5.dp))
                            CenterRow {
                                TextTitleM("Saran dan Tindakan dari AI")
                                Image(
                                    painter = painterResource(R.drawable.ai),
                                    contentDescription = "ai",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .padding(5.dp)
                                )
                            }
                            Spacer(Modifier.padding(3.dp))
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
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        MaterialTheme
                                            .colorScheme
                                            .primary
                                            .copy(alpha = 0.2f)
                                    )
                                    .border(
                                        BorderStroke(
                                            1.dp,
                                            MaterialTheme.colorScheme.primary
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                TextTitleS(
                                    "Level Kerusakan E-waste",
                                    modifier = Modifier.padding(10.dp)
                                )
                                LinearProgressIndicator(
                                    progress = {
                                        listScan[item].damageLvl.toFloat() / 10
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
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        MaterialTheme
                                            .colorScheme
                                            .secondary
                                            .copy(alpha = 0.2f)
                                    )
                                    .border(
                                        BorderStroke(
                                            1.dp,
                                            MaterialTheme.colorScheme.secondary
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    )

                            ) {
                                TextTitleS(
                                    "Level Bahaya E-waste",
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
                                    color = MaterialTheme.colorScheme.secondary,
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
                                    TextContentM(listScan[item].category)
                                    Spacer(Modifier.padding(10.dp))
                                    TextContentM(
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
            SlideReminder(listScan,slideReminder,scrollToLastItem)
        }
        if (!reminder.value) {
            if(detection.objects.isEmpty()){
                NotEwaste(viewModelMain, detection, scanRes.id)
            } else {
                ReminderScanResult(viewModelMain, reminder)
            }
        }
    }
}


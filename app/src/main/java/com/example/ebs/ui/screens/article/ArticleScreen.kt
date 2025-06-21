package com.example.ebs.ui.screens.article

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.ebs.data.structure.remote.ebs.articles.Article
import com.example.ebs.ui.components.shapes.TopBarPage
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentL
import com.example.ebs.ui.components.texts.TextContentM
import com.example.ebs.ui.components.texts.TextTitleL
import com.example.ebs.ui.components.texts.TextTitleM
import com.example.ebs.ui.screens.MainViewModel
import com.example.ebs.ui.screens.detail.WasteDetailViewModel

@Composable
fun ArticleScreen(
    data: Article,
    viewModelMain: MainViewModel,
    viewModel: WasteDetailViewModel = hiltViewModel()
) {
    Log.d("Route", "This is Article")
    TopBarPage("Article", viewModelMain.navHandler){
        Box (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            CenterColumn(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight()
                    .align(TopCenter)
            ) {
                TextTitleL(
                    data.title,
                    modifier = Modifier
                        .align(Start)
                        .padding(
                            top =
                                if (data.imageUrl == "" || data.imageUrl == null)
                                    15.dp
                                else
                                    40.dp
                        ),
                    textAlign =
                        if (data.imageUrl == "" || data.imageUrl == null)
                            TextAlign.Center
                        else
                            TextAlign.Start
                )
                if (data.imageUrl == "" || data.imageUrl == null) {
                    Spacer(modifier = Modifier.padding(bottom = 40.dp))
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(data.imageUrl),
                        contentDescription = "Account Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(bottom = 25.dp, top = 15.dp)
                            .width(300.dp)
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }

                var counterTags = 0
                val tagPerRow = 3

                if(data.tags?.isNotEmpty() == true) {
                    CenterColumn(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .padding(bottom = 15.dp),
                    ) {
                        while(counterTags < data.tags.size) {
                            CenterRow (
                                hArr = Arrangement.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ){
                                data.tags
                                    .filter { data.tags.indexOf(it)-counterTags < tagPerRow }
                                    .takeLast(tagPerRow)
                                    .map { it }
                                    .forEach {
                                        TextContentM(
                                            it,
                                            modifier = Modifier
                                                .padding(4.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primaryContainer else Color.LightGray)
                                                .padding(horizontal = 10.dp, vertical = 4.dp),
                                            textAlign = TextAlign.Start
                                        )
                                    }
                            }
                            counterTags += tagPerRow
                        }
                    }
                }
                data.content.blocks.forEach {
                    when (it.type) {
                        "header" -> {
                            TextTitleM(
                                it.data.text,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 10.dp),
                                textAlign = TextAlign.Start
                            )
                        }

                        "paragraph" -> {
                            TextContentL(
                                it.data.text,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                                textAlign = TextAlign.Start
                            )
                        }

                        "list" -> {
                            when (it.data.style) {
                                "unordered" -> {
                                    it.data.items.forEach { item ->
                                        TextContentL(
                                            "\u2022 ${item.content}",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 20.dp, bottom = 2.dp),
                                            textAlign = TextAlign.Start
                                        )
                                    }
                                }

                                "ordered" -> {
                                    it.data.items.forEachIndexed { idx, item ->
                                        TextContentL(
                                            "${idx + 1}. ${item.content}",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 20.dp, bottom = 2.dp),
                                            textAlign = TextAlign.Start
                                        )
                                    }
                                }

                                "checklist" -> {
                                    it.data.items.forEach { item ->
                                        val check =
                                            if (item.meta.checked == true)
                                                "\u2611"
                                            else
                                                "\u2610"
                                        TextContentL(
                                            "$check ${item.content}",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 20.dp, bottom = 2.dp),
                                            textAlign = TextAlign.Start
                                        )
                                    }
                                }
                            }
                        }

                        "quote" -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                TextContentL(
                                    "<i>\"${it.data.text}\" - ${it.data.caption}</i>",
                                    modifier = Modifier.padding(8.dp),
                                    textAlign = TextAlign.Start
                                )
                            }
                        }

                        "code" -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        MaterialTheme
                                            .colorScheme
                                            .surfaceVariant
                                            .copy(alpha = 0.1f)
                                    )
                            ) {
                                TextContentL(
                                    it.data.code,
                                    modifier = Modifier.padding(8.dp),
                                    textAlign = TextAlign.Start
                                )
                            }
                        }

                        "delimiter" -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                HorizontalDivider()
                            }
                        }

                        "table" -> {
                            SimpleTable(
                                data = it.data.content,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SimpleTable(
    data: List<List<String>>,
    modifier: Modifier = Modifier
) {
    CenterColumn(modifier = modifier) {
        data.forEachIndexed { index, row ->
            CenterRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                row.forEachIndexed { cellIndex, cell ->
                    TextContentM(
                        text = cell,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        textAlign = TextAlign.Start
                    )
                    if (cellIndex < row.lastIndex) {
                        TextContentL("|")
                    }
                }
            }
            if (index < data.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}
package com.example.ebs.ui.screens.article

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.ebs.R
import com.example.ebs.data.structure.remote.ebs.articles.Article
import com.example.ebs.ui.components.shapes.TopBarPage
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.texts.TextContentL
import com.example.ebs.ui.components.texts.TextTitleM
import com.example.ebs.ui.screens.MainViewModel
import com.example.ebs.ui.screens.detail.WasteDetailViewModel

@Composable
fun ArticleScreen(
    navController: NavController,
    data: Article,
    viewModelAuth: MainViewModel,
    viewModel: WasteDetailViewModel = hiltViewModel()
) {
    Log.d("Route", "This is Article")
    TopBarPage("Article", viewModelAuth.navHandler){
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
                TextTitleM(
                    data.title,
                    modifier = Modifier
                        .align(Start),
                    textAlign = TextAlign.Start
                )
                if (data.imageUrl == "") {
                    Image(
                        painterResource(R.drawable.nopicture),
                        contentDescription = "Icon",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
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
                TextContentL(
                    data.content,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}
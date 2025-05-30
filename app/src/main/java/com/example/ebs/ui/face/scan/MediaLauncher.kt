package com.example.ebs.ui.face.scan

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers

@Composable
fun AlbumScreen(modifier: Modifier = Modifier,
                viewModel: AlbumViewModel
) {

    // collecting the flow from the view model as a state allows our ViewModel and View
    // to be in sync with each other.
    val viewState: AlbumViewState by viewModel.viewStateFlow.collectAsState()

    val currentContext = LocalContext.current

    val pickImageFromAlbumLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(20)) { urls ->
        viewModel.onReceive(Intent.OnFinishPickingImagesWith(currentContext, urls))
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isImageSaved ->
        if (isImageSaved) {
            viewModel.onReceive(Intent.OnImageSavedWith(currentContext))
        } else {
            viewModel.onReceive(Intent.OnImageSavingCanceled)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
        if (permissionGranted) {
            viewModel.onReceive(Intent.OnPermissionGrantedWith(currentContext))
        } else {
            // handle permission denied such as:
            viewModel.onReceive(Intent.OnPermissionDenied)
//            or perhaps show a toast
            Toast.makeText(currentContext, "In order to take pictures, you have to allow this app to use your camera", Toast.LENGTH_SHORT).show()
        }
    }

    // this ensures that the camera is launched only once when the url of the temp file changes
    LaunchedEffect(key1 = viewState.tempFileUrl) {
        viewState.tempFileUrl?.let {
            cameraLauncher.launch(it)
        }
    }

    // basic view that has 2 buttons and a grid for selected pictures
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)
        .verticalScroll(rememberScrollState())
        .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Row {
            Button(onClick = {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }) {
                Text(text = "Take a photo")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                pickImageFromAlbumLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }) {
                Text(text = "Pick a picture")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Selected Pictures")
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(0.dp, 1200.dp)
        ) {
            itemsIndexed(viewState.selectedPictures) { index, picture ->
                Image(
                    modifier = Modifier.padding(8.dp),
                    bitmap = picture,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun MainScreenPreview() {
    val viewModel = AlbumViewModel(Dispatchers.Default)
    AlbumScreen(viewModel = viewModel)
}
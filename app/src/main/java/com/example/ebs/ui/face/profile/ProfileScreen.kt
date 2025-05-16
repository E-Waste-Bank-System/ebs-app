package com.example.ebs.ui.face.profile

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.util.CoilUtils.result
import com.example.ebs.R
import com.example.ebs.data.repositories.UserPreferencesRepository
import com.example.ebs.service.AuthResponse
import com.example.ebs.service.WaterNotificationService
import com.example.ebs.ui.face.AuthViewModel
import com.example.ebs.ui.face.components.gradients.getGredienButton
import com.example.ebs.ui.navigation.BotBarPage
import com.example.ebs.ui.navigation.NavigationHandler
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    userPref: UserPreferencesRepository,
    viewModel: ProfileViewModel = hiltViewModel(),
    viewModelAuth: AuthViewModel = hiltViewModel()
) {
    viewModelAuth.initializeNavHandler(navController)
    Log.d("Route", "This is Profile")
    val postNotificationPermission = rememberPermissionState(permission = android.Manifest.permission.POST_NOTIFICATIONS)

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val waterNotificationService = WaterNotificationService(context)

    LaunchedEffect(key1 = true) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        }
    }

    waterNotificationService.showBasicNotification()
    val photo = viewModelAuth.authManagerState.getGoogleProfilePictureUrl()

    BotBarPage(
        navController = navController,
        hazeState = viewModel.hazeState
    ){
        Spacer(modifier = Modifier.height(32.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(168.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        getGredienButton(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
            ){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ){
                    if (photo != null) {
                        Image(
                            painter = rememberAsyncImagePainter(photo),
                            contentDescription = "Account Image",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.nopicture),
                            contentDescription = "Placeholder",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape),
                            tint = Color.Gray
                        )
                    }
                    Text(
                        text = "Aldo Nitehe Lase",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .clickable {
                                viewModelAuth.navHandler.dialogueSetting()
                            }
                    )
                    Text(
                        text = "ldao089@jimail.com | 089789462909",
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .align(Alignment.TopEnd)
                        .clickable{
                            coroutineScope.launch {
                                viewModel.authManagerState.signOut()
                                    .collect{result ->
                                        if (result is AuthResponse.Success) {
                                            userPref.resetAuthToken()
                                            Log.e("Udah Keluar?", "${viewModelAuth.authManagerState.isSignedIn()}")
                                            viewModelAuth.navHandler.welcomeFromMenu()
                                        } else {
                                            Log.d("AuthManager", result.toString())
                                        }
                                    }
                            }
                        }
                ) {
                    Text(
                        text = "Ubah",
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Card(
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.background
            ),
            border = BorderStroke(1.dp,Color.LightGray),
            modifier = Modifier
                .fillMaxWidth(0.85f)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Column {
                ProfileItem("Lokasi",painterResource(R.drawable.map_marker))
                ProfileItem("Bantuan",painterResource(R.drawable.help_circle))
                ProfileItem("Beri Kami Nilai",painterResource(R.drawable.comment_alert))
                ProfileItem("Kontak Kami",painterResource(R.drawable.account_box))
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


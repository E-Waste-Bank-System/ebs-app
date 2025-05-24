package com.example.ebs.ui.screens.profile

import android.Manifest
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
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
import com.example.ebs.R
import com.example.ebs.data.repositories.UserPreferencesRepository
import com.example.ebs.service.WaterNotificationService
import com.example.ebs.service.auth.AuthResponse
import com.example.ebs.ui.components.gradients.getGredienButton
import com.example.ebs.ui.components.inputs.AestheticButton
import com.example.ebs.ui.components.texts.TextTitleM
import com.example.ebs.ui.navigation.BotBarPage
import com.example.ebs.ui.screens.MainViewModel
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
    viewModelAuth: MainViewModel,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    Log.d("Route", "This is Profile")
    val postNotificationPermission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val waterNotificationService = WaterNotificationService(context)

    val userInfo = viewModelAuth.localInfo

    LaunchedEffect(key1 = true) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        }
    }

    if(userInfo.emailVerified == "false") {
        waterNotificationService.showBasicNotification()
    }

    BotBarPage(
        navController = navController,
        modifier = Modifier.padding(top = 25.dp),
        hazeState = viewModelAuth.hazeState
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
                    if (userInfo.picture != null) {
                        Image(
                            painter = rememberAsyncImagePainter(userInfo.picture),
                            contentDescription = "Account Image",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.nopicture),
                            contentDescription = "Placeholder",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Text(
                        text = userInfo.name ?: "No Name",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .clickable {

                            }
                    )
                    Text(
                        text = "${userInfo.email} | ${if (userInfo.emailVerified == "true") "Verified" else "Not Verified"}",
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .clickable {
//                                viewModelAuth.resendVerification()
                            }
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Gray)
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .align(Alignment.TopEnd)
                        .clickable {

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
                ProfileItem(
                    "Lokasi",painterResource(R.drawable.map_marker),
                    modifier = Modifier
                        .clickable {
                            viewModelAuth.navHandler.lokasi()
                        }
                )
                ProfileItem(
                    "Bantuan",painterResource(R.drawable.help_circle),
                    modifier = Modifier
                        .clickable {
                            viewModelAuth.navHandler.bantuan()
                        }
                )
                ProfileItem(
                    "Beri Kami Nilai",painterResource(R.drawable.comment_alert),
                    modifier = Modifier
                        .clickable {
                            viewModelAuth.navHandler.beriNilai()
                        }
                )
                ProfileItem(
                    "Kontak Kami",painterResource(R.drawable.account_box),
                    modifier = Modifier
                        .clickable {
                            viewModelAuth.navHandler.kontak()
                        }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
        AestheticButton (
            onClick = {
                coroutineScope.launch {
                    viewModelAuth.authManagerState.signOut()
                        .collect{result ->
                            if (result is AuthResponse.Success) {
                                Log.e("Udah Keluar?", "${!viewModelAuth.authManagerState.isSignedIn()}")
                                viewModelAuth.navHandler.welcomeFromMenu()
                            } else {
                                Log.d("AuthManager", result.toString())
                            }
                        }
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.85f)
                .height(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Transparent)
                .padding(4.dp),
            color = getGredienButton(
                Color.Red.copy(0.25f),
                MaterialTheme.colorScheme.secondary
            ),
        ){
            TextTitleM("Log out",
                modifier = Modifier
                    .align(Center)
            )
        }
    }
}


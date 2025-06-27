package com.example.ebs.service

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.ebs.utils.MAX_MINOR
import com.example.ebs.utils.MAX_PATCH
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.URL

class UpdateService(
    private val context: Context
) {
    suspend fun getLatestNightlyUrl(
        maxVersion: String = "0.0.0",
        baseVersion: String = "0.0.0",
        fileName: String = "app-release.apk",
        progressor: MutableState<Float> = mutableFloatStateOf(0f),
        wait: MutableState<Boolean> = mutableStateOf(true)
    ): String {
        val maxMajor = maxVersion.split(".").getOrNull(0)?.toIntOrNull() ?: 0
        val maxMinor = maxVersion.split(".").getOrNull(1)?.toIntOrNull() ?: 0
        val maxPatch = maxVersion.split(".").getOrNull(2)?.toIntOrNull() ?: 0
        Log.e("UpdateService", "Checking URL...")
        val baseParts = baseVersion.split(".").map { it.toInt() }
        val urls = mutableListOf<String>()
        for (major in baseParts[0]..maxMajor) {
            val minMinor = if (major == baseParts[0]) baseParts[1] else 0
            val maxMinorLoop = if (major == maxMajor) maxMinor else MAX_MINOR
            for (minor in minMinor..maxMinorLoop) {
                val minPatch = if (major == baseParts[0] && minor == baseParts[1]) baseParts[2] else 0
                val maxPatchLoop = if (major == maxMajor && minor == maxMinor) maxPatch else MAX_PATCH
                for (patch in minPatch..maxPatchLoop) {
                    val version = "$major.$minor.$patch-nightly"
                    val url = "https://github.com/E-Waste-Bank-System/ebs-app/releases/download/v$version/$fileName"
                    urls.add(url)
                }
            }
        }
        // Check which URL exists by making a HEAD request and return the latest available
        for (url in urls.asReversed()) {
            try {
                if(wait.value) {
                    Log.e("UpdateService", "Checking URL: $url ${progressor.value}")
                    val connection =
                        withContext(Dispatchers.IO) {
                            URL(url).openConnection()
                        } as java.net.HttpURLConnection
                    connection.requestMethod = "HEAD"
                    connection.connectTimeout = 2000
                    connection.readTimeout = 2000
                    if (connection.responseCode == 200) {
                        while(progressor.value < 1f) {
                            delay(10)
                            progressor.value += 1f / urls.size
                            Log.e("UpdateService", "Checking URL: $url ${progressor.value} urls.size: ${urls.size}")
                        }
                        connection.disconnect()
                        Log.e("UpdateService", "Done checking URL: $url")
                        return url
                    }
                    connection.disconnect()
                    progressor.value += 1f / urls.size
                }
            } catch (_: Exception) {
                // Ignore and try next
            }
        }
        return ""
    }

    suspend fun downloadAndInstallUpdate(
        wait: MutableState<Boolean> = mutableStateOf(false),
        latest: String? = null,
        progressor: MutableState<Float> = mutableFloatStateOf(0f),
        trigger: MutableState<Boolean> = mutableStateOf(false)
    ) {
        wait.value = !wait.value
        withContext(Dispatchers.IO) {
            val updateUrl = getLatestNightlyUrl(latest ?: "0.0.0", progressor = progressor, wait = wait)
            try {
                Log.e("UpdateService", "Downloading update")
                val request = DownloadManager.Request(updateUrl.toUri())
                    .setTitle("ebs-${updateUrl
                        .substringAfterLast("/")
                        .substringBeforeLast(".")} v${updateUrl
                            .substringAfter("/download/v")
                            .substringBefore("-nightly")}"
                    )
                    .setDescription("Downloading update...")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        "ebs-latest.apk"
                    )
                    .setMimeType("application/vnd.android.package-archive")

                val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val downloadId = dm.enqueue(request)

                val onComplete = object : BroadcastReceiver() {
                    override fun onReceive(ctx: Context, intent: Intent) {
                        try {
                            val uri = dm.getUriForDownloadedFile(downloadId)
                            val installIntent = Intent(Intent.ACTION_VIEW).apply {
                                setDataAndType(uri, "application/vnd.android.package-archive")
                                flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
                            }
                            ctx.startActivity(installIntent)
                        } catch (e: Exception) {
                            Log.e("UpdateService", "Error installing update", e)
                        } finally {
                            ctx.unregisterReceiver(this)
                        }
                    }
                }

                ContextCompat.registerReceiver(
                    context,
                    onComplete,
                    IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                    ContextCompat.RECEIVER_NOT_EXPORTED
                )
                trigger.value = !trigger.value
                wait.value = !wait.value
            } catch (e: Exception) {
                Log.e("UpdateService", "Error downloading update", e)
            }
        }
    }
}
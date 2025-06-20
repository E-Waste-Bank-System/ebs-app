package com.example.ebs.service

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateService(
    private val context: Context
) {

    private fun getLatestUpdateUrl(fileName: String = "app-debug.apk"): String {
        val apiUrl = "https://api.github.com/repos/E-Waste-Bank-System/ebs-app/releases"
        val url = java.net.URL(apiUrl)
        val json = url.readText()
        val versions = Regex("\"tag_name\"\\s*:\\s*\"([^\"]+)\"").findAll(json).map { it.groupValues[1] }.toList()
        val currentVersion = "v0.3.0-nightly"
        val nextVersion = versions.firstOrNull { it > currentVersion } ?: currentVersion
        return "https://github.com/E-Waste-Bank-System/ebs-app/releases/download/$nextVersion/$fileName"
    }

    suspend fun downloadAndInstallUpdate() {
        withContext(Dispatchers.IO) {
            val updateUrl = getLatestUpdateUrl()
            try {
                Log.e("UpdateService", "Downloading update")
                val request = DownloadManager.Request(updateUrl.toUri())
                    .setTitle("EBS Update")
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
            } catch (e: Exception) {
                Log.e("UpdateService", "Error downloading update", e)
            }
        }
    }
}
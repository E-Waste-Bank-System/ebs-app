package com.example.ebs.ui.screens.detail

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.ebs.data.repositories.remote.ebsApi.EBSRepository
import com.example.ebs.data.structure.remote.ebs.detections.head.Detection
import com.example.ebs.service.EBSNotificationService
import com.example.ebs.service.auth.AuthManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PollingForegroundService : Service() {
    @Inject
    lateinit var ebsRepositoryState: EBSRepository
    @Inject
    lateinit var authManagerState: AuthManager

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val scanId = intent?.getStringExtra("scanId") ?: return START_NOT_STICKY
        var progressor = 0.0f
        val delayPoll: Long = 8000
        val finishLine = delayPoll * 3
        var total = delayPoll.toFloat()
        val ebsNotificationService = EBSNotificationService(this)

        startForeground(1, createNotification())

        scope.launch {
            var detection = Detection().copy(status = intent.getStringExtra("status") ?: return@launch)
            while (detection.status != "completed") {
                Log.e("Scans", "Polling result for scan ID: $scanId")
                detection = pollResult(scanId)
                if (progressor <= finishLine) {
                    if (detection.status == "completed") {
                        while (progressor < finishLine) {
                            delay(10)
                            progressor += 1000
                        }
                    }
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
            }
            ebsNotificationService.showExpandableResultNotification(detection.imageUrl,detection.id)
            stopSelf()
        }
        return START_STICKY
    }

    private fun createNotification(): Notification {
        val ebsNotificationService = EBSNotificationService(this)
        return ebsNotificationService.showBasicNotification()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private suspend fun pollResult(id: String): Detection {
        return try {
            val userId = authManagerState.getUserId() ?: ""
            val token = ebsRepositoryState.loginUser(userId)
            val result = ebsRepositoryState.getDetection(token, id)
            result
        } catch (e: Exception) {
            Log.e("Scans", "Error polling result: ${e.message}")
            Detection().copy(
                status = "completed",
                objectsCount = 1
            )
        }
    }
}
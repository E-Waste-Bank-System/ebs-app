package com.example.ebs.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.example.ebs.MainActivity
import com.example.ebs.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class EBSNotificationService(
    private val context: Context
){
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    fun showBasicNotification(): Notification {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("navigate_to", "dashboard")
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(context, "ebs_notification")
            .setContentTitle("EBS Notification")
            .setContentText("Your Scanning in progress.")
            .setSmallIcon(R.drawable.ai)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }
    fun showUnverifiedNotification(){
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("navigate_to", "profile")
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, "ebs_notification")
            .setContentTitle("Silakan Verifikasi Akun Anda")
            .setContentText("Konfirmasi sudah dikirim ke email anda, silakan cek email anda untuk verifikasi akun")
            .setSmallIcon(R.drawable.ai)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }

    suspend fun showExpandableResultNotification(imgUrl:String,id:String){
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("scan_result", "scan_result $id")
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val bitmap = withContext(Dispatchers.IO) {
            Picasso.get()
                .load(imgUrl)
                .get()
        }
        val notification= NotificationCompat.Builder(context,"ebs_notification")
            .setContentTitle("Detection Result Ready")
            .setContentText("Time to see the results of your scan!")
            .setSmallIcon(R.drawable.ai)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat
                    .BigPictureStyle()
                    .bigPicture(
                        bitmap
                    )
            )
            .build()
        notificationManager.notify(Random.nextInt(),notification)
    }

    private fun Context.bitmapFromResource(
        @DrawableRes resId:Int
    )= BitmapFactory.decodeResource(
        resources,
        resId
    )
}
//
//class WaterNotificationService(
//    private val context: Context
//) {
//    private val notificationManager = context.getSystemService(NotificationManager::class.java)
//
//    fun showBasicNotification() {
//        val notification = NotificationCompat.Builder(context, "water_reminder")
//            .setContentTitle("Water Reminder")
//            .setContentText("Time to drink some water!")
//            .setSmallIcon(R.drawable.ai)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setAutoCancel(true)
//            .build()
//
//        notificationManager.notify(
//            Random.nextInt(),
//            notification
//        )
//    }
//
//    fun showExpandableNotification() {
//        val image = context.bitmapFromResource(R.drawable.water)
//
//        val notification = NotificationCompat.Builder(context, "water_reminder")
//            .setContentTitle("Water Reminder")
//            .setContentText("Time to drink some water!")
//            .setSmallIcon(R.drawable.ai)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setLargeIcon(image)
//            .setStyle(
//                NotificationCompat
//                    .BigPictureStyle()
//                    .bigPicture(image)
//                    .bigLargeIcon(null as Bitmap?)
//            )
//            .setAutoCancel(true)
//            .build()
//
//        notificationManager.notify(
//            Random.nextInt(),
//            notification
//        )
//    }
//
//    fun showExpandableLongText() {
//        val notification = NotificationCompat.Builder(context, "water_reminder")
//            .setContentTitle("Water Reminder")
//            .setContentText("Time to drink some water!")
//            .setSmallIcon(R.drawable.ai)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setStyle(
//                NotificationCompat
//                    .BigTextStyle()
//                    .bigText("Very big text")
//            )
//            .setAutoCancel(true)
//            .build()
//
//        notificationManager.notify(
//            Random.nextInt(),
//            notification
//        )
//    }
//
//    fun showInboxStyleNotification() {
//        val notification = NotificationCompat.Builder(context, "water_reminder")
//            .setContentTitle("Water Reminder")
//            .setContentText("Time to drink some water!")
//            .setSmallIcon(R.drawable.ai)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setStyle(
//                NotificationCompat
//                    .InboxStyle()
//                    .addLine("Line 1")
//                    .addLine("Line 2")
//                    .addLine("Line 3")
//                    .addLine("Line 4")
//                    .addLine("Line 5")
//                    .addLine("Line 6")
//                    .addLine("Line 7")
//            )
//            .setAutoCancel(true)
//            .build()
//
//        notificationManager.notify(
//            Random.nextInt(),
//            notification
//        )
//    }
//
//    fun showNotificationGroup() {
//        val groupId = "water_group"
//        val summaryId = 0
//
//        val notification1 = NotificationCompat.Builder(context, "water_reminder")
//            .setContentTitle("Water Reminder")
//            .setContentText("Time to drink some water!")
//            .setSmallIcon(R.drawable.ai)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setStyle(
//                NotificationCompat
//                    .InboxStyle()
//                    .addLine("Line 1")
//            )
//            .setAutoCancel(true)
//            .setGroup(groupId)
//            .build()
//
//        val notification2 = NotificationCompat.Builder(context, "water_reminder")
//            .setContentTitle("Water Reminder")
//            .setContentText("Time to drink some water!")
//            .setSmallIcon(R.drawable.ai)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setStyle(
//                NotificationCompat
//                    .InboxStyle()
//                    .addLine("Line 1")
//                    .addLine("Line 2")
//            )
//            .setAutoCancel(true)
//            .setGroup(groupId)
//            .build()
//
//        val summaryNotification = NotificationCompat.Builder(context, "water_reminder")
//            .setContentTitle("Water Reminder")
//            .setContentText("Time to drink some water!")
//            .setSmallIcon(R.drawable.ai)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setStyle(
//                NotificationCompat
//                    .InboxStyle()
//                    .setSummaryText("Water reminders missed")
//                    .setBigContentTitle("Water Reminders")
//            )
//            .setAutoCancel(true)
//            .setGroup(groupId)
//            .setGroupSummary(true)
//            .build()
//
//        notificationManager.notify(
//            Random.nextInt(),
//            notification1
//        )
//        notificationManager.notify(
//            Random.nextInt(),
//            notification2
//        )
//        notificationManager.notify(
//            Random.nextInt(),
//            summaryNotification
//        )
//    }
//
//    private fun Context.bitmapFromResource(
//        @DrawableRes resId: Int
//    ) = BitmapFactory.decodeResource(
//        resources,
//        resId
//    )
//}
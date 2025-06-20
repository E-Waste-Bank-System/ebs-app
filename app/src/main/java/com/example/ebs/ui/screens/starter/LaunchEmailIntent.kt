package com.example.ebs.ui.screens.starter

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri

@SuppressLint("QueryPermissionsNeeded")
fun launchEmailIntent(context: Context) {
    val intent = context.packageManager.getLaunchIntentForPackage("com.google.android.gm")
//    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
//        The 'mailto:' URI scheme targets email applications.
//        data = "mailto:".toUri()
//        putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
//        putExtra(Intent.EXTRA_SUBJECT, subject)
//    }

    if (intent != null) {
        context.startActivity(intent)
    } else {
        val webIntent = Intent(Intent.ACTION_VIEW, "https://mail.google.com/".toUri())
        try {
            context.startActivity(webIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No email or web browser client found.", Toast.LENGTH_LONG).show()
        }
    }
}
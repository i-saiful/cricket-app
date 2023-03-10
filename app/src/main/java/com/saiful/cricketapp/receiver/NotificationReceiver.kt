package com.saiful.cricketapp.receiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.saiful.cricketapp.R
import com.saiful.cricketapp.ui.MainActivity

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)
        val message = intent?.getStringExtra("message") ?: "Welcome to Cricket App"
        val title = intent?.getStringExtra("title") ?: "Cricket App"

        val notification = NotificationCompat.Builder(context, "channel_id")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.app_icon)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationId = System.currentTimeMillis().toInt()
        notificationManager.notify(notificationId, notification)
    }
}
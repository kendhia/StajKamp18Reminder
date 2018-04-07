package me.projects.kendhia.stajkamp18reminder.services

import android.widget.Toast
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = NotificationManagerCompat.from(context)

        val mBuilder = NotificationCompat.Builder(context, "remindercamp")
                .setContentTitle("Reminder")
                .setContentText("it's time for your alarm.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        notificationManager.notify(123, mBuilder.build())
    }
}
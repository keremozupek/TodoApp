package com.example.todoapp.managers

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.todoapp.notification.ReminderReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TodoAlarmManager @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val alarmManager: AlarmManager by lazy {
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private val notificationManager: NotificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {
        initNotificationChannel()
    }

    private fun initNotificationChannel() {
        val name: CharSequence = "todoappReminderChannel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("todoapp", name, importance)
        val description = "Channel For Alarm Manager"
        channel.description = description
        notificationManager.createNotificationChannel(channel)
    }

    fun setAlarm(title: String, requestCode: Int, alarmTime: Long) {
        val intent = Intent(context, ReminderReceiver::class.java)
        intent.putExtra("title", title)
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode + 1, intent, 0)
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
    }

    fun cancelAlarm(id: Int) {
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0)
        alarmManager.cancel(pendingIntent)
    }

}
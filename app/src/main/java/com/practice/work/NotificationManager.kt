package com.practice.work

import android.app.*
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log


object NotificationManager {
    val DEADLINE_NOTIFICATION_CHANNEL_ID = "666"

    lateinit var describe_notification: String

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "deadlines"
            val descriptionText = "AAAAAAA"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(DEADLINE_NOTIFICATION_CHANNEL_ID, name, importance)
            mChannel.description = descriptionText

            val notificationManager =
                context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
        Log.i("NOTIFY", "channel created")
    }

    fun setNotification(context: Context, time: Long, desc: String) {
        describe_notification = desc
        val notifyIntent = Intent(context, Receiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 1, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent)
    }


    class NotifyService : IntentService("NotificationManager") {
        override fun onHandleIntent(intent: Intent?) {
            makeNotification(this)
            Log.i("NOTIFY", "It's work!!!")
        }
    }

    class Receiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            val intent1 = Intent(context, NotifyService::class.java)
            context.startService(intent1)
        }
    }




    private fun makeNotification(context: Context) {
        val notificationIntent = Intent(context, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
            context,
            0, notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val builder = NotificationCompat.Builder(context, DEADLINE_NOTIFICATION_CHANNEL_ID)

        builder.setContentIntent(contentIntent)
            // обязательные настройки
            .setSmallIcon(R.drawable.bomb)
            .setContentTitle("Час икс наступил")
            .setContentText(describe_notification) // Текст уведомления
            // необязательные настройки
            .setTicker("Последнее китайское предупреждение!")
            .setWhen(System.currentTimeMillis())
            .setAutoCancel(true)


        val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManager.notify(1, builder.build())
    }
}

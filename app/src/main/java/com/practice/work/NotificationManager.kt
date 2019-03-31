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
import android.app.AlarmManager
import android.app.PendingIntent
import com.practice.work.activities.MainActivity
import com.practice.work.activities.TimeTableActivity
import java.text.SimpleDateFormat
import java.util.*

//import java.util.*

//еще раз
object NotificationManager {
    //можно повесить настройки
     const val DEADLINE_NOTIFICATION_CHANNEL_ID = "666"
     const val SUGGESTION_NOTIFICATION_CHANNEL_ID = "777"

    //создание группы для уведомлений
    fun createNotificationChannel(context: Context, channelName: String, desc: String, channel_id: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(channel_id, channelName, importance)
            mChannel.description = desc

            val notificationManager =
                context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
        Log.i("NOTIFY", "channel created")
    }

    //настройка периодичности проверки на свободное время
    //вызывается каждый час с момента загрузки
    fun setNotification(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            pendingIntent)
    }

    //условия вызова уведомления
    class NotifyService : IntentService("NotificationManager") {
        override fun onHandleIntent(intent: Intent?) {
            val now = SimpleDateFormat("HH").format(Calendar.getInstance().time)
            Log.i("NOTIFY", now)
//            if (TimeTableActivity.tasks[now] != null)
//                Log.i("NOTIFY", TimeTableActivity.tasks[now].key)key
            if (TimeTableActivity.tasks[now]?.text == "task")
                makeNotification(this, DEADLINE_NOTIFICATION_CHANNEL_ID, "Хотите сделать что-нибудь полезное?", "Кажется, сейчас свободное время")
            Log.i("NOTIFY", "It's work!!!")
        }
    }

    class AlarmReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val intent1 = Intent(context, NotifyService::class.java)
            context.startService(intent1)
        }
    }


    //возвращает 8 часов текущего дня (начало отсчета для уведомлений)
    private fun getTime(hour: String, min: String): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        //Настраиваем время (здесь 8 утра) отправки ежедневного уведомления
        calendar.set(
            Calendar.HOUR_OF_DAY,
            Integer.getInteger(hour, 8)!!,
            Integer.getInteger(min, 0)!!
        )
        return calendar.timeInMillis
    }


    //билд и вызов оповещения
     private fun makeNotification(context: Context, channel_id: String, describe_notification: String, title: String) {
        val notificationIntent = Intent(context, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
            context,
            0, notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val builder = NotificationCompat.Builder(context, channel_id)

        builder.setContentIntent(contentIntent)
            // обязательные настройки
            .setSmallIcon(R.drawable.bomb)
            .setContentTitle(title)
            .setContentText(describe_notification) // Текст уведомления
            // необязательные настройки
            .setTicker("Последнее китайское предупреждение!")
            .setWhen(System.currentTimeMillis())
            .setAutoCancel(true)


        val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(context)
        val n = builder.build()
        notificationManager.notify(1, n)
    }
}

package com.practice.work.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.practice.work.NotificationManager
import com.practice.work.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val intent = Intent(this,MainActivity::class.java)
                startActivityForResult(intent, 0)
            }
            R.id.navigation_dashboard -> {
                val intent = Intent(this, TimeTableActivity::class.java)
                startActivityForResult(intent, 0)
            }
            R.id.navigation_notifications -> {
                val intent = Intent(this, TaskActivity::class.java)
                startActivityForResult(intent, 0)
            }
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        NotificationManager.createNotificationChannel(
                this,
        "sugg",
        "",
        NotificationManager.DEADLINE_NOTIFICATION_CHANNEL_ID
        ) //may be zakommentirovat'
        NotificationManager.createNotificationChannel(this, "sugg","", NotificationManager.DEADLINE_NOTIFICATION_CHANNEL_ID)
        NotificationManager.setNotification(this)
    }


}

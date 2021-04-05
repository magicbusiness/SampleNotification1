package com.example.samplenotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder

class MainActivity : AppCompatActivity()
{
    companion object
    {
        const val CHANNEL_ID = "CHANNEL_ID"
        const val CHANNEL_NAME = "CHANNEL_NAME"
        const val NOTIFICATION_ID = 0
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //
        val btnNotification: Button = findViewById(R.id.btn_notification)

        //
        val intentHome = Intent(this, MainActivity::class.java)

        // Notification can be navigate
        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intentHome)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        //
        createNotification()

        // Notification Content
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Sample Notification Title")
                .setContentText("This is Content Text")
                .setSmallIcon(R.drawable.vector_notification)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()

        val notificationManager = NotificationManagerCompat.from(this)


        btnNotification.setOnClickListener {
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    fun createNotification()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                // Change LED
                lightColor = Color.RED
                enableLights(true)
            }

            // make notification service
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager // must add NotificationManager, if not component will be any?
            manager.createNotificationChannel(channel)
        }
    }
}
package com.example.coursework

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.coursework.ui.account.ui.TopicsFragment


class NotificationJobService : JobService() {

    lateinit var  mNotifyManager : NotificationManager

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        createNotificationChannel()
        val pendIntent = PendingIntent.getActivity(applicationContext, 0,
                Intent(applicationContext, TopicsFragment::class.java), PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(applicationContext, PRIMARY_CHANNEL_ID)
                .setContentTitle("Check out these Articles!").setContentText("These articles just have been posted")
                .setContentIntent(pendIntent)
                .setSmallIcon(R.mipmap.ic_article)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
        mNotifyManager.notify(0,builder.build())
        return false
    }

    private fun createNotificationChannel() {

        mNotifyManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel = NotificationChannel(PRIMARY_CHANNEL_ID,
                "Job Service notification",
                NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = "Notifications from Job Service"
        mNotifyManager.createNotificationChannel(notificationChannel)
    }


    companion object {
        val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }

}
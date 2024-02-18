package com.rzsahu.logextractor

import android.R
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.Environment
import android.os.Environment.DIRECTORY_DOCUMENTS
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.File


class LogExtractorService : Service() {

    private val TAG = "MyLog.LogExtractorService"
    private val NOTIFICATION_ID = 1
    private val CHANNEL_ID = "ForegroundServiceChannel"
    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        // Initialization code for your service
        Log.i(TAG, "onCreate: ")
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand: ")
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, buildNotification());

        ioScope.launch {
            val path = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS)
            val outputFile = File(path, "logs.txt")
            LogExtractor.extractAndSaveLogsContainingWord("MyLog.", outputFile)
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up code for your service
        Log.i(TAG, "onDestroy: ")
        LogExtractor.stop()
        ioScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        // Return null because this service does not support binding
        Log.i(TAG, "onBind: ")
        return null
    }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Foreground Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(
            NotificationManager::class.java
        )
        manager.createNotificationChannel(serviceChannel)
    }

    private fun buildNotification(): Notification {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("Service is running in the foreground")
            .setSmallIcon(R.drawable.ic_notification_overlay)
        return builder.build()
    }
}


package com.rzsahu.logextractor

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {

    private val TAG = "MyLog.MainActivity"

    private var bound = false
    private lateinit var textMsg: TextView

    private fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "onCreate: ")


        findViewById<Button>(R.id.start).setOnClickListener {
            Log.i(TAG, "onClick: start")
            if (!isServiceRunning(this, LogExtractorService::class.java)) {
                startService(Intent(this, LogExtractorService::class.java))
                textMsg.text = "Log service is started."
            } else {
                textMsg.text = "Log service is already running."
            }
        }

        findViewById<Button>(R.id.stop).setOnClickListener {
            Log.i(TAG, "onClick: stop")
            if (isServiceRunning(this, LogExtractorService::class.java)) {
                stopService(Intent(this, LogExtractorService::class.java))
                textMsg.text = "Log service is stopped now."
            } else {
                textMsg.text = "Log service not stated yet. Press save log!!"
            }
        }

        findViewById<Button>(R.id.generate_log).setOnClickListener {
            Log.i(TAG, "onClick: generate random logs")
            var i = 0
            for (i in 0..10) {
                Log.i(TAG, "onCreate: hello")
            }
        }
        textMsg = findViewById(R.id.textmsg)
    }
}
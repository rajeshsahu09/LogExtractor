package com.rzsahu.logextractor

import android.annotation.SuppressLint
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
    private lateinit var pref: SharedPreferenceLogService


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "onCreate: ")

        pref = SharedPreferenceLogService(this@MainActivity)

        findViewById<Button>(R.id.start).setOnClickListener {
            Log.i(TAG, "onClick: start")
            if (!pref.isServiceRunning) {
                pref.isServiceRunning =
                        startService(Intent(this, LogExtractorService::class.java)) != null
                textMsg.text = "Log service is started."
            } else {
                textMsg.text = "Log service is already running."
            }
        }

        findViewById<Button>(R.id.stop).setOnClickListener {
            Log.i(TAG, "onClick: stop")
            if (pref.isServiceRunning) {
                pref.isServiceRunning = !stopService(Intent(this, LogExtractorService::class.java))
                textMsg.text = "Log service is stopped now."
            } else {
                textMsg.text = "Log service not stated yet. Press save log!!"
            }
        }

        findViewById<Button>(R.id.generate_log).setOnClickListener {
            Log.i(TAG, "onClick: generate random logs")
            var i = 0
            for(i in 0..10) {
                Log.i(TAG, "onCreate: hello")
            }
        }
        textMsg = findViewById(R.id.textmsg)
    }
}
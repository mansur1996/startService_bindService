package com.example.modul6lesson8serviceandbroadcastreceiver.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.widget.Toast

//BackgroundService
class StartedService : Service() {
    private var player : MediaPlayer? = null
    private val TAG = "StartedServce"

    override fun onCreate() {
        Toast.makeText(this, "Started Service Created", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onCreate: ")
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind: ")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)

        player!!.isLooping = true
        player!!.start()
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onStartCommand: ")
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        //Stopping the player when service is destroyed
        player!!.stop()
        Toast.makeText(this, "Started Service Stopped", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onDestroy: ")
    }
}
package com.example.modul6lesson8serviceandbroadcastreceiver.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer
import android.widget.Toast

class BoundService : Service() {
    private val mBinder: IBinder = MyBinder()

    //it shows time that it is changed realtime
    private var mChronometer: Chronometer? = null

    private val TAG = "BoundService"

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "Bound Service Created", Toast.LENGTH_LONG).show()
        Log.d(TAG, "onCreate: ")

        mChronometer = Chronometer(this)
        mChronometer!!.base = SystemClock.elapsedRealtime()
        mChronometer!!.onChronometerTickListener =
            Chronometer.OnChronometerTickListener { chronometer ->
                Toast.makeText(application, chronometer.base.toString(), Toast.LENGTH_LONG).show()
            }
        mChronometer!!.start()
    }

    override fun onBind(intent: Intent?): IBinder? {
        Toast.makeText(this, "Bound Service OnBind", Toast.LENGTH_LONG).show()
        Log.d(TAG, "onBind: ")
        return mBinder
    }

    override fun onRebind(intent: Intent?) {
        Toast.makeText(this, "Bound Service OnReBind", Toast.LENGTH_LONG).show()
        Log.d(TAG, "onRebind: ")
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Toast.makeText(this, "Bound Service OnUnbind", Toast.LENGTH_LONG).show()
        Log.d(TAG, "onUnbind: ")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Bound Service Stopped", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onDestroy: ")
        mChronometer!!.stop()
    }

    val timestamp: String
        get() {
            val elapsedMillis = SystemClock.elapsedRealtime() - mChronometer!!.base
            val hours = (elapsedMillis / 3600000).toInt()
            val minutes = (elapsedMillis - hours * 3600000).toInt() / 60000
            val seconds = (elapsedMillis - hours * 3600000 - minutes * 60000).toInt() / 1000
            val millis =
                (elapsedMillis - hours * 3600000 - minutes * 60000 - seconds * 1000).toInt()
            return "$hours:$minutes:$seconds:$millis"
        }

    inner class MyBinder : Binder() {
        // Return this instance of localService so clients can call public method
        fun getBoundService(): BoundService = this@BoundService
    }
}
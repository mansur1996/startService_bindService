package com.example.modul6lesson8serviceandbroadcastreceiver.activity

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.example.modul6lesson8serviceandbroadcastreceiver.databinding.ActivityMainBinding
import com.example.modul6lesson8serviceandbroadcastreceiver.services.BoundService
import com.example.modul6lesson8serviceandbroadcastreceiver.services.MyJobService
import com.example.modul6lesson8serviceandbroadcastreceiver.services.StartedService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var boundService: BoundService? = null
    var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

        val jobInfo = JobInfo.Builder(1, ComponentName(this, MyJobService::class.java))

        val job = jobInfo.setRequiresCharging(true).setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setOverrideDeadline(15 * 1000)
            .build()

        jobScheduler.schedule(job)

        initViews()
    }


    private fun initViews() {

        binding.btnStartStarted.setOnClickListener {
            startStartedService()
        }

        binding.btnStopStarted.setOnClickListener {
            stopStartedService()
        }

        binding.btnPrintTimestamp.setOnClickListener {
            if (isBound) {
                binding.tvTimestamp.text = boundService!!.timestamp
            }
        }

        binding.btnStartBound.setOnClickListener {
            startBoundService()
        }

        binding.btnStopBound.setOnClickListener {
            stopBoundService()
        }

    }

    private fun startStartedService() {
        val intent = Intent(this, StartedService::class.java)
        startService(intent)
    }

    private fun stopStartedService() {
        val intent = Intent(this, StartedService::class.java)
        stopService(intent)
    }

    private fun startBoundService() {
        val intent = Intent(this, BoundService::class.java)
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE)
    }

    private fun stopBoundService() {
        if (isBound) {
            unbindService(mServiceConnection)
            isBound = false
        }
    }

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val myBinder: BoundService.MyBinder = service as BoundService.MyBinder
            boundService = myBinder.getBoundService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }
}
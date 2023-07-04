package com.example.modul6lesson8serviceandbroadcastreceiver.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import android.widget.Toast

class MyJobService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        Toast.makeText(this, "Job started", Toast.LENGTH_SHORT).show()
        Log.e("TAG", "onStartJob: ", )
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Toast.makeText(this, "Job stopped", Toast.LENGTH_SHORT).show()
        Log.e("TAG", "onStopJob: ", )
        return true
    }
}
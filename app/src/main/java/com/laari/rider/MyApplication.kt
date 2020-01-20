package com.laari.rider

import android.app.Application
import android.os.StrictMode


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }
}
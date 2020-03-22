package com.example.nikidemoapp

import android.app.Application
import io.paperdb.Paper

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Paper.init(applicationContext)
    }
}
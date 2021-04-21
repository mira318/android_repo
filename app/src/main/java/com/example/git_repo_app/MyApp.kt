package com.example.git_repo_app

import android.app.Application
import android.content.Context

class MyApp: Application() {

    lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
    }

    companion object {
        lateinit var instance: MyApp
        private set
    }

    fun getAppContext(): Context {
        return context
    }
}
package com.abrar.akudimana.core.app

import android.app.Application
import androidx.multidex.MultiDex
import com.abrar.akudimana.core.di.module.AppModule
import com.abrar.akudimana.core.di.module.DatabaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainApplication)
            modules(listOf(AppModule, DatabaseModule))
        }

    }
}
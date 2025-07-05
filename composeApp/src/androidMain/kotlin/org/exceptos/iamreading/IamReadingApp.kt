package org.exceptos.iamreading

import android.app.Application
import org.exceptos.iamreading.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class IamReadingApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@IamReadingApp)
            androidLogger()
            modules(appModule())
        }

    }
}
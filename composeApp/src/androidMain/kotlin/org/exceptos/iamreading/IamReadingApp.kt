package org.exceptos.iamreading

import android.app.Application
import org.exceptos.iamreading.handlers.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class IamReadingApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin(
            config = {
                androidContext(this@IamReadingApp)
                androidLogger()
            }
        )

    }
}
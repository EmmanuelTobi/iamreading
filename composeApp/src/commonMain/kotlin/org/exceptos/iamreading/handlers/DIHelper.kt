package org.exceptos.iamreading.handlers

import org.exceptos.iamreading.di.appModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}
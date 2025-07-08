package org.exceptos.iamreading.handlers

import org.exceptos.iamreading.di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule())
    }
}
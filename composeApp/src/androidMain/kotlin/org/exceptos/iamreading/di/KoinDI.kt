package org.exceptos.iamreading.di

import org.exceptos.iamreading.data.db.AppDatabase
import org.exceptos.iamreading.db.getDatabaseBuilder
import org.koin.dsl.module

actual fun platformModule() = module {
    single<AppDatabase> { getDatabaseBuilder(get()) }
}
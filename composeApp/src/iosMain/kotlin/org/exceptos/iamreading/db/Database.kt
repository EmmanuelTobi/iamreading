package org.exceptos.iamreading.db

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.exceptos.iamreading.data.db.AppDatabase
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): AppDatabase {
    val dbFile = "${NSHomeDirectory()}/iamreading.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile,
        factory = { AppDatabase::class.instantiateImpl() }
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
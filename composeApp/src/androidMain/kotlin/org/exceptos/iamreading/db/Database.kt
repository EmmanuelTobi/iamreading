package org.exceptos.iamreading.db

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.exceptos.iamreading.data.db.AppDatabase

fun getDatabaseBuilder(ctx: Context): AppDatabase {
    val dbFile = ctx.getDatabasePath("iamreading.db")
    return Room.databaseBuilder<AppDatabase>(ctx, dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
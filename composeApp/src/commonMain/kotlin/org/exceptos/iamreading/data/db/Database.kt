package org.exceptos.iamreading.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import org.exceptos.iamreading.data.model.Book
import org.exceptos.iamreading.data.dao.BookDao
import org.exceptos.iamreading.data.dao.BookNoteDao
import org.exceptos.iamreading.data.dao.StatsDao
import org.exceptos.iamreading.data.model.BookNotes
import org.exceptos.iamreading.data.model.Stats

@Database(entities = [Book::class, Stats::class, BookNotes::class], version = 1, exportSchema = true )
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase(), DBInterface {

    abstract fun getBookDao(): BookDao
    abstract fun getStatsDao(): StatsDao
    abstract fun getBookNotesDao(): BookNoteDao

    override fun clearAllTables(): Unit {
    }
}

interface DBInterface {
    fun clearAllTables(): Unit {}
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}


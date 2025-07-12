package org.exceptos.iamreading.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import org.exceptos.iamreading.data.model.Book
import org.exceptos.iamreading.data.dao.BookDao
import org.exceptos.iamreading.data.dao.BookNoteDao
import org.exceptos.iamreading.data.dao.StatsDao
import org.exceptos.iamreading.data.model.BookNotes
import org.exceptos.iamreading.data.model.Stats

//@ConstructedBy(AppDatabase::class)
@Database(entities = [Book::class, Stats::class, BookNotes::class], version = 1, exportSchema = true )
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
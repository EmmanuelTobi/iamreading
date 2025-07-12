package org.exceptos.iamreading.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import org.exceptos.iamreading.data.model.Book
import org.exceptos.iamreading.data.dao.BookDao

//@ConstructedBy(AppDatabase::class)
@Database(entities = [Book::class], version = 1)
abstract class AppDatabase : RoomDatabase(), DBInterface {
    abstract fun getBookDao(): BookDao
    override fun clearAllTables(): Unit {
    }
}

interface DBInterface {
    fun clearAllTables(): Unit {}
}
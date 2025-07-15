package org.exceptos.iamreading.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.exceptos.iamreading.data.model.BookNotes

@Dao
interface BookNoteDao {
    @Query("SELECT * FROM book_notes")
    fun getAll(): List<BookNotes>

    @Query("SELECT * FROM book_notes WHERE bookName LIKE :title LIMIT 1")
    fun findByTitle(title: String): Flow<List<BookNotes>>

    @Insert
    fun insertAll(vararg bookNotes: BookNotes)

    @Delete
    fun delete(bookNote: BookNotes)

    @Query("DELETE FROM book_notes")
    fun deleteAll()

}
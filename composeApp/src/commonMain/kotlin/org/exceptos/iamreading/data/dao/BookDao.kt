package org.exceptos.iamreading.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.exceptos.iamreading.data.model.Book

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE status = :status")
    fun getBooksByStatus(status: String): Flow<List<Book>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Query("UPDATE books SET title = :title, author = :author, imageUrl = :imageUrl, description = :description WHERE id = :id")
    suspend fun updateBook(id: Int, title: String, author: String, description: String, imageUrl: String?)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("DELETE FROM books")
    suspend fun deleteAllBooks()
}
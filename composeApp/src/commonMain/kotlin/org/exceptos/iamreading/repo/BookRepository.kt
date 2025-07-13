package org.exceptos.iamreading.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.Clock.System
import org.exceptos.iamreading.data.dao.BookDao
import org.exceptos.iamreading.data.dao.StatsDao
import org.exceptos.iamreading.data.model.Book
import org.exceptos.iamreading.data.model.BookStatus
import org.exceptos.iamreading.data.model.Stats

class BookRepository(private val bookDao: BookDao, private val statsDao: StatsDao) {

    var bookStatus : BookStatus = BookStatus.CURRENTLY_READING

    fun getAllBooks(): Flow<List<Book>> = bookDao.getAllBooks()

    fun getBooksByStatus(status: String): Flow<List<Book>> =
        bookDao.getBooksByStatus(status)

    fun getBookStatByType(type: String) : Flow<Stats?> {
        return statsDao.getStatsByType(type)
    }

    suspend fun setBookStat(type: String) {
        val existingStats: Stats? = getBookStatByType(type).firstOrNull()

        if (existingStats == null) {
            println("Stat for type '$type' not found. Inserting new stat.")
            val newStat = Stats(
                statsType = type,
                statsCount = 1,
                lastUpdated = System.now().toString() // Use Clock.System
            )
            statsDao.insert(newStat)
        } else {

            println("Stat for type '$type' found. Updating count from ${existingStats.statsCount}.")

            val updatedCount = existingStats.statsCount + 1
            val newLastUpdated = System.now().toString()

            statsDao.updateStatsByType(
                type = type,
                value = updatedCount,
                lastUpdated = newLastUpdated
            )

        }

    }

    suspend fun insertBook(
        title: String,
        author: String,
        description: String,
        imageUrl: String?,
        status: String
    ) {

        val book = Book(
            title = title,
            author = author,
            description = description,
            imageUrl = imageUrl,
            status = status,
            totalNotes = 0
        )

        println(book.author)
        bookDao.insertBook(book)

    }

    suspend fun deleteBook(book: Book) = bookDao.deleteBook(book)

    suspend fun deleteAllBooks() = bookDao.deleteAllBooks()

    suspend fun updateBook(
        id: Int,
        title: String,
        author: String,
        description: String,
        imageUrl: String?,
        status: BookStatus
    ) {
        bookDao.updateBook(id, title, author, description, imageUrl, status.name, 0)
    }
}
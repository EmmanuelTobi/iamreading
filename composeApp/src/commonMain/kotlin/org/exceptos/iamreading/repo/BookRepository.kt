package org.exceptos.iamreading.repo

import kotlinx.coroutines.flow.Flow
import org.exceptos.iamreading.data.dao.BookDao
import org.exceptos.iamreading.data.model.Book
import org.exceptos.iamreading.data.model.BookStatus

class BookRepository(private val bookDao: BookDao) {

    var bookStatus : BookStatus = BookStatus.CURRENTLY_READING

    fun getAllBooks(): Flow<List<Book>> = bookDao.getAllBooks()

    fun getBooksByStatus(status: String): Flow<List<Book>> =
        bookDao.getBooksByStatus(status)

//    fun setBookStatus(status: BookStatus) {
//        bookStatus = status
//        println(bookStatus)
//    }

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
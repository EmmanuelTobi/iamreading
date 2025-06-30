package org.exceptos.iamreading.screens.book_lists

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.exceptos.iamreading.data.model.Book
import org.exceptos.iamreading.data.model.BookStatus
import org.exceptos.iamreading.repo.BookRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BookListsViewModel : ViewModel(), KoinComponent {
    private val bookRepository: BookRepository by inject()

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    fun loadBooks(status: BookStatus) {
        viewModelScope.launch {
            bookRepository.getBooksByStatus(status.toString()).collect { bookList ->
                _books.value = bookList
            }
        }
    }

    fun addBook(title: String, author: String, description: String, imageUrl: String?, status: BookStatus) {
        viewModelScope.launch {
            bookRepository.insertBook(title, author, description, imageUrl, status.toString())
        }
    }

    fun updateBook(id: Int, title: String, author: String, description: String, imageUrl: String?, status: BookStatus) {
        viewModelScope.launch {
            bookRepository.updateBook(id, title, author, description, imageUrl, status)
        }
    }

    fun deleteBook(id: Int) {
        viewModelScope.launch {
            bookRepository.deleteBook(_books.value.find { it.id == id }!!)
        }
    }
}
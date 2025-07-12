package org.exceptos.iamreading.screens.book_lists

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.exceptos.iamreading.data.model.Book
import org.exceptos.iamreading.data.model.BookStatus
import org.exceptos.iamreading.handlers.ResultHandler
import org.exceptos.iamreading.repo.BookRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BookListsViewModel(status: BookStatus? = null) : ViewModel(), KoinComponent {
    private val bookRepository: BookRepository by inject()

    private val _books = MutableStateFlow<ResultHandler<List<Book>>>(ResultHandler.Loading)
    val books: StateFlow<ResultHandler<List<Book>>> = _books.asStateFlow()

    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook: StateFlow<Book?> = _selectedBook.asStateFlow()

//    init {
//        loadBooks(status ?: BookStatus.CURRENTLY_READING )
//    }

    fun setBookStatus(status: BookStatus) {
        bookRepository.bookStatus = status
        loadBooks(status)
    }

    fun loadBooks(status: BookStatus) {
        _books.value = ResultHandler.Loading

        viewModelScope.launch {
            bookRepository.getBooksByStatus(status.toString()).collect { bookList ->
                if(bookList.isEmpty())
                    _books.value = ResultHandler.Success(emptyList())
                else
                _books.value = ResultHandler.Success(bookList)
            }
        }

    }

    fun addBook(
        title: String,
        author: String,
        description: String,
        imageUrl: String?,
        status: BookStatus
    ) {

        println(bookRepository.bookStatus);

        viewModelScope.launch {
            bookRepository.insertBook(title, author, description, imageUrl, status.toString())
        }
    }

    fun updateBook(id: Int, title: String, author: String, description: String, imageUrl: String?, status: BookStatus) {
        viewModelScope.launch {
            bookRepository.updateBook(id, title, author, description, imageUrl, status)
        }
    }

    suspend fun deleteBook(id: Int?, book: Book?) {

        if (book != null) {

            _selectedBook.value = book

        } else if(id != null) {

            _selectedBook.value = _books.collect {
                if(it is ResultHandler.Success) {
                    it.data.find { it.id == id }
                }
            }

        }

        if(_selectedBook.value == null)
            return

        viewModelScope.launch {
            bookRepository.deleteBook(_selectedBook.value!!)
        }
    }
}
package org.exceptos.iamreading.screens.book_details

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.exceptos.iamreading.data.model.Book
import org.exceptos.iamreading.data.model.BookNotes
import org.exceptos.iamreading.data.model.BookStatus
import org.exceptos.iamreading.repo.BookRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BookDetailsViewModel : ViewModel(), KoinComponent {
    private val bookRepository: BookRepository by inject()

    private val _book = MutableStateFlow<Book?>(null)
    val book: StateFlow<Book?> = _book.asStateFlow()

    private val _bookNotes = MutableStateFlow<List<BookNotes>>(emptyList())
    val bookNotes: StateFlow<List<BookNotes>> = _bookNotes.asStateFlow()

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _aiResponse = MutableStateFlow<String?>(null)
    val aiResponse: StateFlow<String?> = _aiResponse.asStateFlow()

    fun loadBook(id: Int) {
        viewModelScope.launch {
            bookRepository.getBookById(id).let { book ->
                _book.value = book.firstOrNull()
            }
        }
    }

    fun updateBookStatus(status: BookStatus) {
        _book.value?.let { currentBook ->
            viewModelScope.launch {
                bookRepository.updateBook(
                    id = currentBook.id,
                    title = currentBook.title,
                    author = currentBook.author,
                    description = currentBook.description,
                    imageUrl = currentBook.imageUrl,
                    status = status
                )
            }
        }
    }

    fun updateCurrentPage(page: Int) {
        _currentPage.value = page
    }

    fun addBookNote(note: String, page: Int) {
        _book.value?.let { currentBook ->
            if (currentBook.status == BookStatus.CURRENTLY_READING.toString()) {
                viewModelScope.launch {
                    bookRepository.addBookNote(
                        description = note,
                        bookName = currentBook.title,
                        noteFromPage = page
                    )
                }
            }
        }
    }

    fun deleteBook() {
        _book.value?.let { currentBook ->
            viewModelScope.launch {
                bookRepository.deleteBook(currentBook)
            }
        }
    }

    fun loadBookNotes() {
        _book.value?.let { currentBook ->
            viewModelScope.launch {
                bookRepository.getBookNotes(currentBook.title).collect { notes ->
                    _bookNotes.value = notes
                }
            }
        }
    }

    fun analyzeBookWithAI(question: String, analyzeNotes: Boolean = false) {
        viewModelScope.launch {
            val book = _book.value
            if (book != null) {
                val context = buildString {
                    append("Book Title: ${book.title}\n")
                    append("Author: ${book.author}\n")
                    append("Description: ${book.description}\n")
                    
                    if (analyzeNotes && _bookNotes.value.isNotEmpty()) {
                        append("\nReader's Notes:\n")
                        _bookNotes.value.forEach { note ->
                            append("- Page ${note.noteFromPage}: ${note.description}\n")
                        }
                    }
                }
                
                // TODO: Implement actual AI service integration
                // For now, return a mock response
                _aiResponse.value = generateMockAIResponse(question, analyzeNotes)
            }
        }
    }

    private fun generateMockAIResponse(question: String, includesNotes: Boolean): String {
        return if (includesNotes) {
            "Based on your notes and the book content, here's an analysis of the themes and key points you've highlighted..."
        } else {
            "Here's an analysis of the book's themes, characters, and overall message..."
        }
    }
}
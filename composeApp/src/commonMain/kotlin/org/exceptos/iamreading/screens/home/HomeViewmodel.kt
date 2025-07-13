package org.exceptos.iamreading.screens.home

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import org.exceptos.iamreading.repo.BookRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class LibraryStats (
    var currentlyReading: Int = 0,
    var read: Int = 0,
    var toRead: Int = 0,
)

class HomeViewmodel() : ViewModel(), KoinComponent   {
    private val bookRepository: BookRepository by inject()

    private val _libraryStats = MutableStateFlow<LibraryStats?>(null)
    val libraryStats: StateFlow<LibraryStats?> = _libraryStats.asStateFlow()

    suspend fun setLibraryStats() {

        val currentlyReadingStats = bookRepository.getBookStatByType("CURRENTLY_READING").firstOrNull()
        val finishedStats = bookRepository.getBookStatByType("FINISHED").firstOrNull()
        val wantToReadStats = bookRepository.getBookStatByType("WANT_TO_READ").firstOrNull()

        _libraryStats.value = LibraryStats(
            currentlyReading = currentlyReadingStats?.statsCount ?: 0,
            read = finishedStats?.statsCount ?: 0,
            toRead = wantToReadStats?.statsCount ?: 0
        )
    }

}
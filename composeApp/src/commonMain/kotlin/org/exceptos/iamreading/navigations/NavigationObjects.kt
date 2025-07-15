package org.exceptos.iamreading.navigations

import kotlinx.serialization.Serializable
import org.exceptos.iamreading.data.model.BookStatus

class NavigationObjects {

    @Serializable
    object HomeScreen

    @Serializable
    data class BookListsScreen (
        val status: BookStatus
    )

    @Serializable
    object AddBookScreen

    @Serializable
    data class BookDetailsScreen (
        val bookId: Int
    )

}
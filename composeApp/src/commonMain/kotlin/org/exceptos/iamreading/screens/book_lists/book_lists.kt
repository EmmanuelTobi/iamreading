package org.exceptos.iamreading.screens.book_lists

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.exceptos.iamreading.data.model.Book
import org.exceptos.iamreading.data.model.BookStatus
import org.exceptos.iamreading.views.BookItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookLists(
    status: BookStatus = BookStatus.WANT_TO_READ,
    onNavigateToAddBook: () -> Unit = {}) {
    var showAddBookDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(getScreenTitle(status)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddBook) {
                Icon(Icons.Filled.Add, contentDescription = "Add Book")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // This will be populated with actual data from the database
            items(emptyList<Book>()) { book ->
                BookItem(
                    drawableResource = null,
                    title = book.title,
                    description = book.description,
                    author = book.author
                )
            }

        }
    }
}

private fun getScreenTitle(status: BookStatus): String {
    return when (status) {
        BookStatus.CURRENTLY_READING -> "Currently Reading"
        BookStatus.WANT_TO_READ -> "Want to Read"
        BookStatus.FINISHED -> "Finished Books"
    }
}
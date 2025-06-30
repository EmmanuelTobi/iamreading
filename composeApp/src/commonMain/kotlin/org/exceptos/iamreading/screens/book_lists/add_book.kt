package org.exceptos.iamreading.screens.book_lists

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.exceptos.iamreading.data.model.Book
import org.exceptos.iamreading.data.model.BookStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBook(
    onNavigateBack: () -> Unit = {}
) {

    var bookListViewModel: BookListsViewModel = remember { BookListsViewModel() }

    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Book") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) { 
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Author") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Button(
                onClick = {
                    if (title.isNotBlank() && author.isNotBlank()) {
                        val book = Book(
                            title = title,
                            author = author,
                            description = description,
                            imageUrl = null,
                            status = BookStatus.WANT_TO_READ.toString()
                        )

                        bookListViewModel.addBook(
                            title = title,
                            author = author,
                            description = description,
                            imageUrl = null,
                            status = BookStatus.WANT_TO_READ
                        )

                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Book")
            }
        }
    }
}
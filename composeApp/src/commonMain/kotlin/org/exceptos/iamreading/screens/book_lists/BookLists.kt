package org.exceptos.iamreading.screens.book_lists

import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.drop
import org.exceptos.iamreading.data.model.BookStatus
import org.exceptos.iamreading.handlers.ResultHandler
import org.exceptos.iamreading.views.BookItem
import org.exceptos.iamreading.widgets.BottomSheets
import org.exceptos.iamreading.widgets.LoadingView
import org.exceptos.iamreading.widgets.TextWidget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookLists(
    status: BookStatus = BookStatus.WANT_TO_READ,
    onNavigateToAddBook: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    viewModel: BookListsViewModel = remember {
        BookListsViewModel()
    }
) {

    val openBottomSheet = remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(getScreenTitle(status)) },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.books.drop(1)
                        onNavigateBack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back")
                    }
                },
//                actions = {
//                    IconButton(onClick = { onNavigateToAddBook() }) {
//                        Icon(Icons.Filled.Add, contentDescription = "Add Book")
//                    }
//                }
            )
        },
        floatingActionButton = {

            if(openBottomSheet.value == true) {

                BottomSheets(
                    onDismiss = {
                        openBottomSheet.value = false
                        viewModel.books.drop(1)
                        onNavigateBack()
                    },
                    content = {
                        AddBook(
                            onNavigateBack = {
                                viewModel.books.drop(1)
                                onNavigateBack()
                            }
                        )
                    },
                    title = "Add New Book"
                )

            } else {

                FloatingActionButton(
                    onClick = {
                        onNavigateToAddBook()
                        //openBottomSheet.value = true    ///will be revisiting later for better implementation
                    }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Book")
                }

            }

        }
    ) { paddingValues ->

        LaunchedEffect(
            key1 = status,
            block = {
                viewModel.setBookStatus(status)
            }
        )

        val books by viewModel.books.collectAsState()
        books.let { result ->

            when(result) {
                is ResultHandler.Success -> {
                    println(result.data)

                    if(result.data.isEmpty()) {

                        Column (
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextWidget(
                                text = "No books found",
                                fontSize = 20.sp,
                            )
                        }

                    } else if(result.data.isNotEmpty()) {

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            items(result.data) { book ->
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
                is ResultHandler.Error -> {
                    println("Error occurred")
                }
                is ResultHandler.Loading -> {
                    LoadingView()
                }
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
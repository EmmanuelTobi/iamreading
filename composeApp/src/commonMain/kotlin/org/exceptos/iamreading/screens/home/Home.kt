package org.exceptos.iamreading.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import org.jetbrains.compose.resources.painterResource
import iamreading.composeapp.generated.resources.Res
import iamreading.composeapp.generated.resources.ic_menu
import iamreading.composeapp.generated.resources.ic_search
import iamreading.composeapp.generated.resources.done
import iamreading.composeapp.generated.resources.reading
import iamreading.composeapp.generated.resources.toread
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import iamreading.composeapp.generated.resources.ic_add
import iamreading.composeapp.generated.resources.ic_book
import org.exceptos.iamreading.data.model.BookStatus
import org.exceptos.iamreading.screens.book_lists.BookLists
import org.exceptos.iamreading.views.BookItem
import org.exceptos.iamreading.views.LibraryItem
import org.exceptos.iamreading.widgets.TextWidget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    onNavigateToBookLists: (BookStatus) -> Unit,
    onNavigateToAddBook: () -> Unit,
    onNavigateToBookDetails: (Int) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "IAM Reading",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                navigationIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_book),
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_search),
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LibraryContent(
            paddingValues,
            onNavigateToBookLists,
            onNavigateToAddBook,
            onNavigateToBookDetails
        )
    }
}

@Composable
fun LibraryContent(
    paddingValues: PaddingValues,
    onNavigateToBookLists: (BookStatus) -> Unit,
    onNavigateToAddBook: () -> Unit,
    onNavigateToBookDetails: (Int) -> Unit,
    viewModel: HomeViewmodel = remember {
    HomeViewmodel()
}) {
    var selectedBookStatus by remember { mutableStateOf<BookStatus?>(null) }

    if (selectedBookStatus != null) {
        BookLists(
            status = selectedBookStatus!!,
            onNavigateToAddBook = onNavigateToAddBook,
            onNavigateToBookDetails = onNavigateToBookDetails
        )
        return
    }

    LaunchedEffect(
        key1 = "library",
        block = {
            viewModel.setLibraryStats()
            viewModel.loadAllBooks()
        }
    )

    val libraryStats = viewModel.libraryStats.collectAsState().value
    val allBooks = viewModel.allBooks.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            // My Library Section
            TextWidget(
                text = "My Library",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Library Items
            LibraryItem(
                drawableResource = Res.drawable.reading,
                title = "Currently Reading",
                count = "${libraryStats?.currentlyReading ?: 0} books",
                color = MaterialTheme.colorScheme.primary,
                onClick = {
                    selectedBookStatus = BookStatus.CURRENTLY_READING
                    onNavigateToBookLists(selectedBookStatus!!)
                }
            )
            LibraryItem(
                drawableResource = Res.drawable.toread,
                title = "Want to Read",
                count = "${libraryStats?.toRead ?: 0} books",
                color = MaterialTheme.colorScheme.secondary,
                onClick = {
                    selectedBookStatus = BookStatus.WANT_TO_READ
                    onNavigateToBookLists(selectedBookStatus!!)
                }
            )
            LibraryItem(
                drawableResource = Res.drawable.done,
                title = "Finished",
                count = "${libraryStats?.read ?: 0} books",
                color = MaterialTheme.colorScheme.tertiary,
                onClick = {
                    selectedBookStatus = BookStatus.FINISHED
                    onNavigateToBookLists(selectedBookStatus!!)
                }
            )

            Spacer(
                modifier = Modifier.padding(8.dp)
            )

            // My History Section
            TextWidget(
                text = "My History",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
            )

            if (allBooks.isEmpty()) {
                // Empty History View
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "No reading history yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Button(onClick = onNavigateToAddBook) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_add),
                            contentDescription = "Add Book",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add First Book")
                    }
                }
            } else {
                    // Show all added books
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        allBooks.forEach { book ->
                            BookItem(
                                drawableResource = null,
                                title = book.title,
                                description = book.description,
                                author = book.author,
                                status = book.status,
                                modifier = Modifier.clickable {
                                    onNavigateToBookDetails(book.id)
                                }
                            )
                        }
                    }
                }
        }
    }

}
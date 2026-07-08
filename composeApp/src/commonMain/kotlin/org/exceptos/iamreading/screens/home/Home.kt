package org.exceptos.iamreading.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import iamreading.composeapp.generated.resources.Res
import iamreading.composeapp.generated.resources.book_one
import iamreading.composeapp.generated.resources.book_two
import iamreading.composeapp.generated.resources.done
import iamreading.composeapp.generated.resources.reading
import iamreading.composeapp.generated.resources.toread
import org.exceptos.iamreading.data.model.BookStatus
import org.exceptos.iamreading.screens.book_lists.BookLists
import org.exceptos.iamreading.views.BookItem
import org.exceptos.iamreading.views.LibraryItem
import org.exceptos.iamreading.widgets.TextWidget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(onNavigateToBookLists: (BookStatus) -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "IAM Reading",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
//                navigationIcon = {
//                    Icon(
//                        imageVector = Icons.Default.Edit,
//                        contentDescription = "Menu",
//                        tint = MaterialTheme.colorScheme.primary
//                    )
//                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Filled.Search,
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
            onNavigateToBookLists
        )
    }
}

@Composable
fun LibraryContent(
    paddingValues: PaddingValues,
    onNavigateToBookLists: (BookStatus) -> Unit,
    viewModel: HomeViewmodel = remember {
    HomeViewmodel()
}) {
    var selectedBookStatus by remember { mutableStateOf<BookStatus?>(null) }

    if (selectedBookStatus != null) {
        BookLists(status = selectedBookStatus!!, onNavigateToAddBook = {})
        return
    }

    LaunchedEffect(
        key1 = "library",
        block = {
            viewModel.setLibraryStats()
        }
    )

    viewModel.libraryStats.collectAsState().let {

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
                    count = "${it.value?.currentlyReading.toString()} books",
                    color = MaterialTheme.colorScheme.primary,
                    onClick = {
                        println(viewModel.libraryStats.value)
                        selectedBookStatus = BookStatus.CURRENTLY_READING
                        onNavigateToBookLists(selectedBookStatus!!)
                    }
                )
                LibraryItem(
                    drawableResource = Res.drawable.toread,
                    title = "Want to Read",
                    count = "${it.value?.toRead} books",
                    color = MaterialTheme.colorScheme.secondary,
                    onClick = {
                        selectedBookStatus = BookStatus.WANT_TO_READ
                        onNavigateToBookLists(selectedBookStatus!!)
                    }
                )
                LibraryItem(
                    drawableResource = Res.drawable.done,
                    title = "Finished",
                    count = "${it.value?.read} books",
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

                // Book Items
                BookItem(
                    drawableResource = Res.drawable.book_one,
                    title = "The Silent Patient",
                    description = "A gripping psychological thriller",
                    author = "Alex Michaelides"
                )
                BookItem(
                    drawableResource = Res.drawable.book_one,
                    title = "Where the Crawdads Sing",
                    description = "A coming-of-age story set in the marshes",
                    author = "Delia Owens"
                )
                BookItem(
                    drawableResource = Res.drawable.book_two,
                    title = "The Seven Husbands of Evelyn Hugo",
                    description = "A captivating tale of a Hollywood icon",
                    author = "Taylor Jenkins Reid"
                )
            }
        }

    }

}
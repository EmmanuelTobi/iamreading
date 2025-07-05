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
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

enum class Screen {
    ForYou, Explore, MyLibrary, Settings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(onNavigateToBookLists: (BookStatus) -> Unit) {
    var currentScreen by remember { mutableStateOf(Screen.MyLibrary) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("IAM Reading") },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "For You") },
                    label = { Text("For You") },
                    selected = currentScreen == Screen.ForYou,
                    onClick = { currentScreen = Screen.ForYou }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Explore") },
                    label = { Text("Explore") },
                    selected = currentScreen == Screen.Explore,
                    onClick = { currentScreen = Screen.Explore }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.AccountBox, contentDescription = "My Library") },
                    label = { Text("My Library") },
                    selected = currentScreen == Screen.MyLibrary,
                    onClick = { currentScreen = Screen.MyLibrary }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = currentScreen == Screen.Settings,
                    onClick = { currentScreen = Screen.Settings }
                )
            }
        }
    ) { paddingValues ->
        when (currentScreen) {
            Screen.MyLibrary -> LibraryContent(
                paddingValues,
                onNavigateToBookLists
            )
            Screen.ForYou -> Text("For You Screen")
            Screen.Explore -> Text("Explore Screen")
            Screen.Settings -> Text("Settings Screen")
        }
    }
}

@Composable
fun LibraryContent(paddingValues: PaddingValues, onNavigateToBookLists: (BookStatus) -> Unit) {
    var selectedBookStatus by remember { mutableStateOf<BookStatus?>(null) }

    if (selectedBookStatus != null) {
        BookLists(status = selectedBookStatus!!, onNavigateToAddBook = {})
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // My Library Section
            TextWidget(
                text = "My Library",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Library Items
            LibraryItem(
                drawableResource = Res.drawable.reading,
                title = "Currently Reading",
                count = "3 items",
                onClick = {
                    selectedBookStatus = BookStatus.CURRENTLY_READING
                    onNavigateToBookLists(selectedBookStatus!!)
                }
            )
            LibraryItem(
                drawableResource = Res.drawable.toread,
                title = "Want to Read",
                count = "15 items",
                onClick = {
                    selectedBookStatus = BookStatus.WANT_TO_READ
                    onNavigateToBookLists(selectedBookStatus!!)
                }
            )
            LibraryItem(
                drawableResource = Res.drawable.done,
                title = "Finished",
                count = "22 items",
                onClick = {
                    selectedBookStatus = BookStatus.FINISHED
                    onNavigateToBookLists(selectedBookStatus!!)
                }
            )

            Spacer(
                modifier = Modifier.padding(12.dp)
            )

            // My History Section
            TextWidget(
                text = "My History",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
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
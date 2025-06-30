package org.exceptos.iamreading


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.exceptos.iamreading.AddBookScreen
import org.exceptos.iamreading.data.model.BookStatus
import org.exceptos.iamreading.screens.book_lists.AddBook
import org.exceptos.iamreading.screens.book_lists.BookLists
import org.exceptos.iamreading.screens.home.Home
import org.jetbrains.compose.ui.tooling.preview.Preview

@Serializable
object HomeScreen

@Serializable
data class BookListsScreen (
    val status: BookStatus
)

@Serializable
object AddBookScreen

@Serializable
object BookDetailsScreen

@Composable
@Preview
fun App() {
    MaterialTheme (
//        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = androidx.compose.ui.graphics.Color.Black
        )

    ) {

        Surface {
            val navController : NavHostController = rememberNavController()
            NavHost( navController = navController, startDestination = HomeScreen) {
                composable<HomeScreen> {
                    Home(
                        onNavigateToBookLists = { status ->
                            navController.navigate(BookListsScreen(status))
                        }
                    )
                }
                composable<BookListsScreen> { backStackEntry ->
                    BookLists(
                        status = backStackEntry.toRoute<BookListsScreen>().status,
                        onNavigateToAddBook = {
                            navController.navigate(AddBookScreen)
                        }
                    )
                }
                composable<AddBookScreen> {
                    AddBook(
                        onNavigateBack = {
                            navController.popBackStack()
                        },
                    )
                }

            }
        }

    }
}
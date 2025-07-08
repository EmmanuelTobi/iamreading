package org.exceptos.iamreading.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.exceptos.iamreading.screens.book_lists.AddBook
import org.exceptos.iamreading.screens.book_lists.BookLists
import org.exceptos.iamreading.screens.home.Home

@Composable
fun NavigationHostHandler(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationObjects.HomeScreen) {

        composable<NavigationObjects.HomeScreen> {
            Home(
                onNavigateToBookLists = { status ->
                    navController.navigate(NavigationObjects.BookListsScreen(status))
                }
            )
        }

        composable<NavigationObjects.BookListsScreen> { backStackEntry ->
            BookLists(
                status = backStackEntry.toRoute<NavigationObjects.BookListsScreen>().status,
                onNavigateToAddBook = {
                    navController.navigate(NavigationObjects.AddBookScreen)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<NavigationObjects.AddBookScreen> {
            AddBook(
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}
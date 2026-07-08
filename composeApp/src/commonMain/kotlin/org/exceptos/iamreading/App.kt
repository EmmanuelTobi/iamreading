package org.exceptos.iamreading


import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.exceptos.iamreading.navigations.NavigationHostHandler
import org.exceptos.iamreading.themes.IamReadingTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    IamReadingTheme {
        Surface {
            val navController : NavHostController = rememberNavController()
            NavigationHostHandler(navController)
        }
    }
}
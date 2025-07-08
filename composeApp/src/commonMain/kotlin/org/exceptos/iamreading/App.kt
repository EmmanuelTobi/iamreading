package org.exceptos.iamreading


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.exceptos.iamreading.navigations.NavigationHostHandler
import org.jetbrains.compose.ui.tooling.preview.Preview

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
            NavigationHostHandler(navController)

        }

    }
}
package com.mikhaellopez.presentation.scenes.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mikhaellopez.presentation.extensions.showUrlInBrowser
import com.mikhaellopez.presentation.scenes.carddetail.CardDetailScreen

@Composable
fun MainScene() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavScreen.Home.route,
    ) {
        composable(route = NavScreen.Home.route) {
            HomeScreen(openCard = { card ->
                navController.navigate(NavScreen.CardDetail.createRoute(card.name))
            })
        }
        composable(
            route = NavScreen.CardDetail.route,
            arguments = listOf(
                navArgument(NavScreen.CardDetail.argName) { nullable = true }
            )
        ) { backStackEntry ->
            val cardName = backStackEntry.arguments?.getString(NavScreen.CardDetail.argName)
                ?: return@composable
            val context = LocalContext.current
            CardDetailScreen(
                cardName = cardName,
                pressOnBack = { navController.navigateUp() },
                openLink = { url -> context.showUrlInBrowser(url) }
            )
        }
    }
}

sealed class NavScreen(val route: String) {

    object Home : NavScreen("Home")

    object CardDetail : NavScreen("CardDetail/{name}") {
        const val argName: String = "name"

        fun createRoute(cardName: String): String = route
            .replace("{$argName}", cardName)
    }
}

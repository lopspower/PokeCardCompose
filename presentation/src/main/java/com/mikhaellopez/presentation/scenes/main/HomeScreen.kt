package com.mikhaellopez.presentation.scenes.main

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mikhaellopez.domain.model.Card
import com.mikhaellopez.presentation.R
import com.mikhaellopez.presentation.scenes.cardlist.CardListScreen

@Composable
fun HomeScreen(
    openCard: (Card) -> Unit
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                LazyBottomNavigationItem(
                    navController,
                    currentDestination,
                    BottomNavScreen.CardGrid
                )
                LazyBottomNavigationItem(
                    navController,
                    currentDestination,
                    BottomNavScreen.CardList
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavScreen.CardGrid.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavScreen.CardGrid.route) {
                CardListScreen(
                    gridMode = true,
                    openCard = openCard
                )
            }
            composable(BottomNavScreen.CardList.route) {
                CardListScreen(
                    gridMode = false,
                    openCard = openCard
                )
            }
        }
    }
}

@Composable
private fun RowScope.LazyBottomNavigationItem(
    navController: NavHostController,
    currentDestination: NavDestination?,
    screen: BottomNavScreen
) {
    BottomNavigationItem(
        icon = { Icon(screen.imageVector, contentDescription = null) },
        label = { Text(stringResource(screen.resourceId)) },
        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
        onClick = {
            navController.navigate(screen.route) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        }
    )
}

sealed class BottomNavScreen(
    val route: String,
    @StringRes val resourceId: Int,
    val imageVector: ImageVector
) {
    object CardGrid :
        BottomNavScreen("CardGrid", R.string.card_grid, Icons.Default.CalendarViewMonth)

    object CardList : BottomNavScreen("CardList", R.string.card_list, Icons.Default.List)
}
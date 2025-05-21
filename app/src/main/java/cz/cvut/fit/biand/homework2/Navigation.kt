package cz.cvut.fit.biand.homework2

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cz.cvut.fit.biand.homework2.screens.DetailScreen
import cz.cvut.fit.biand.homework2.screens.FavoritesScreen
import cz.cvut.fit.biand.homework2.screens.ListScreen
import cz.cvut.fit.biand.homework2.screens.Screens
import cz.cvut.fit.biand.homework2.screens.SearchScreen

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    ) {
    NavHost(modifier = modifier, navController = navController, startDestination = Screens.TopLevel.Characters) {
        composable<Screens.TopLevel.Characters> {
            ListScreen(navController)
        }

        composable<Screens.CharacterDetail> { entry ->
            DetailScreen(navController)
        }

        composable<Screens.Search> {
            SearchScreen(navController)
        }
        composable<Screens.TopLevel.Favorites> {
            FavoritesScreen(navController)
        }
    }
}

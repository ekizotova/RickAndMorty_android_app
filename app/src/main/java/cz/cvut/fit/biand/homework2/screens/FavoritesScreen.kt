package cz.cvut.fit.biand.homework2.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import cz.cvut.fit.biand.homework2.content.FavoritesScreenContent
import cz.cvut.fit.biand.homework2.models.FavoritesViewModel

@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    val screenState by viewModel.screenStateStream.collectAsStateWithLifecycle()

    FavoritesScreenContent (
        screenState = screenState,
        onNavigateBackClick = { navController.popBackStack() },
        onCharacterClick = { character ->
            navController.navigate(Screens.CharacterDetail(character.id))
        }
    )
}

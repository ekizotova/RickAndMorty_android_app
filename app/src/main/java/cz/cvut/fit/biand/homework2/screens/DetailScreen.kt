package cz.cvut.fit.biand.homework2.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import cz.cvut.fit.biand.homework2.data.Character
import cz.cvut.fit.biand.homework2.models.DetailScreenState
import cz.cvut.fit.biand.homework2.models.DetailViewModel
import cz.cvut.fit.biand.homework2.R
import cz.cvut.fit.biand.homework2.data.mapText

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel(),
){
    val screenState by viewModel.screenStateStream.collectAsStateWithLifecycle()

    DetailScreen(
        screenState = screenState,
        onFavoriteClick = viewModel::onFavoriteClick,
        onNavigateBackClick = { navController.popBackStack() },
    )
}

@Composable
private fun DetailScreen(
    screenState: DetailScreenState,
    onFavoriteClick: () -> Unit,
    onNavigateBackClick: () -> Unit,
) {
    screenState.character?.let { character ->
        Scaffold(
            topBar = {
                DetailTopBar(
                    character = character,
                    favorite = screenState.favorite,
                    onNavigateBackClick = onNavigateBackClick,
                    onFavoriteClick = onFavoriteClick,
                )
            },
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ) {
            Box(modifier = Modifier.padding(it)) {
                CharacterDetail(character)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopBar(
    character: Character,
    favorite: Boolean,
    onNavigateBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigateBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(id = R.string.back),
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
        title = {
            Text(
                text = character.name,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        actions = {
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    painter = if (character.isFavorite) {
                        painterResource(id = R.drawable.ic_favorites_filled)
                    } else {
                        painterResource(id = R.drawable.ic_favorites)
                    },
                    tint = if (character.isFavorite) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                    contentDescription = stringResource(id = R.string.favorite),
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
private fun CharacterDetail(character: Character) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            CharacterCardHeader(character)
            Spacer(modifier = Modifier.height(16.dp))
            CharacterCardDetails(character)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CharacterCardHeader(character: Character) {
    Row(modifier = Modifier.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.tertiaryContainer,
            shape = RoundedCornerShape(16.dp)
        )
    ) {
        AsyncImage(
            model = character.imageUrl,
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentDescription = "Image",
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
        ) {
            Text(
                text = stringResource(R.string.name),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = character.name,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
private fun CharacterCardDetails(character: Character) {
    Column(
        modifier = Modifier
            .fillMaxWidth(fraction = 2 / 3f)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Information(title = stringResource(R.string.status), value = mapText(character.status))
        Information(title = stringResource(R.string.species), value = character.species)
        Information(title = stringResource(R.string.type), value = character.type)
        Information(title = stringResource(R.string.gender), value = mapText(character.gender))
        Information(title = stringResource(R.string.origin), value = character.origin)
        Information(title = stringResource(R.string.location), value = character.location)
    }
}

@Composable
private fun Information(title: String, value: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSecondary,
        )
        Text(
            text = value.takeUnless { it.isBlank() } ?: "-",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

package cz.cvut.fit.biand.homework2.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.biand.homework2.api.CharacterApi
import cz.cvut.fit.biand.homework2.data.toDomain
import cz.cvut.fit.biand.homework2.data.toEntity
import cz.cvut.fit.biand.homework2.data.Character
import cz.cvut.fit.biand.homework2.data.CharacterDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val characterApi: CharacterApi,
    private val characterDao: CharacterDAO
) : ViewModel() {
    private val _screenStateStream = MutableStateFlow(ListScreenState())
    val screenStateStream = _screenStateStream.asStateFlow()

    init {
        viewModelScope.launch {
            ensureCharactersFetched()

            characterDao.getAllFlow().collect { entities ->
                val characters = entities.map { it.toDomain() }
                _screenStateStream.value = ListScreenState(characters = characters)
            }
        }
    }

    private suspend fun ensureCharactersFetched() {
        val localCharacters = characterDao.getAll()
        if (localCharacters.isEmpty()) {
            val favorites = characterDao.getFavoritesFlow().first().associateBy { it.id }

            val remoteCharacters = characterApi.getCharacters().map { dto ->
                val isFavorite = favorites[dto.id]?.isFavorite ?: false
                dto.toEntity().copy(isFavorite = isFavorite)
            }

            characterDao.insertAll(remoteCharacters)
        }
    }
/*
    private suspend fun fetchAndStoreCharacters() {
        val existingFavorites = characterDao.getFavoritesFlow().first().associateBy { it.id }

        val characters = characterApi.getCharacters().map { dto ->
            val isFavorite = existingFavorites[dto.id]?.isFavorite ?: false
            dto.toEntity().copy(isFavorite = isFavorite)
        }

        characterDao.insertAll(characters)
    }

    private suspend fun loadCharactersFromDatabase() {
        var characters = characterDao.getAll().map { it.toDomain() }
        if (characters.isEmpty()) {
            fetchAndStoreCharacters()
            characters = characterDao.getAll().map { it.toDomain() }
        }
        _screenStateStream.value = ListScreenState(
            characters = characters
        )
    }

 */
}

data class ListScreenState(
    val characters: List<Character> = emptyList()
)


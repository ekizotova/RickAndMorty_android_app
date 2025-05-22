package cz.cvut.fit.biand.homework2.models

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import cz.cvut.fit.biand.homework2.api.CharacterApi
import cz.cvut.fit.biand.homework2.data.toDomain
import cz.cvut.fit.biand.homework2.data.toEntity
import cz.cvut.fit.biand.homework2.data.Character
import cz.cvut.fit.biand.homework2.screens.Screens
import cz.cvut.fit.biand.homework2.data.CharacterDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val characterDAO: CharacterDAO,
    private val characterApi: CharacterApi,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val id: Long
        get() = savedStateHandle.toRoute<Screens.CharacterDetail>().id

    private val _screenStateStream = MutableStateFlow(DetailScreenState())
    val screenStateStream = _screenStateStream.asStateFlow()

    init {
        viewModelScope.launch {
            characterDAO.getCharacterFlowById(id.toInt())
                .collect { entity ->
                    _screenStateStream.value = DetailScreenState(
                        character = entity?.toDomain()
                    )

                    if (entity == null) {
                        val downloadedCharacter = characterApi.getCharacterById(id.toInt())
                        downloadedCharacter?.let {
                            characterDAO.insertAll(listOf(it.toEntity()))
                        }
                    }
                }
        }
    }

    fun onFavoriteClick() {
        viewModelScope.launch {
            val current = screenStateStream.value.character ?: return@launch
            characterDAO.updateFavoriteStatus(current.id.toInt(), !current.isFavorite)
        }
    }
}

data class DetailScreenState(
    val character: Character? = null,
    val favorite: Boolean = false,
)

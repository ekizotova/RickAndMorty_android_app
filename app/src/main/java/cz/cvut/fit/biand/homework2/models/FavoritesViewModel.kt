package cz.cvut.fit.biand.homework2.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.biand.homework2.data.toDomain
import cz.cvut.fit.biand.homework2.data.Character
import cz.cvut.fit.biand.homework2.data.CharacterDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val characterDAO: CharacterDAO
) : ViewModel() {

    private val _screenStateStream = MutableStateFlow(FavoritesScreenState())
    val screenStateStream = _screenStateStream.asStateFlow()

    init {
        viewModelScope.launch {
            characterDAO.getFavoritesFlow().collect { entities ->
                _screenStateStream.value = FavoritesScreenState(
                    favorites = entities.map { it.toDomain() }
                )
            }
        }
    }
}

data class FavoritesScreenState(
    val favorites: List<Character> = emptyList()
)


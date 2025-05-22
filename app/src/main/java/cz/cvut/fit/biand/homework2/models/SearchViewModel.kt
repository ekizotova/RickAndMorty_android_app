package cz.cvut.fit.biand.homework2.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fit.biand.homework2.api.CharacterApi
import cz.cvut.fit.biand.homework2.data.toDomain
import cz.cvut.fit.biand.homework2.data.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val characterApi: CharacterApi
) : ViewModel() {
    private val _screenStateStream = MutableStateFlow(SearchScreenState())
    val screenStateStream = _screenStateStream.asStateFlow()

    fun searchCharacters(query: String) {
        if (query.isBlank()) {
            clearText()
        } else {

            viewModelScope.launch {
                // maybe add delay ?

                val characters = try {
                    characterApi.searchCharactersByName(query).map { it.toDomain() }
                } catch (e: Exception) {
                    emptyList()
                }
                _screenStateStream.update {
                    it.copy(
                        query = query,
                        characters = characters
                    )
                }
            }
        }
    }

    fun clearText() {
        _screenStateStream.update {
            it.copy(
                query = "",
                characters = emptyList()
            )
        }
    }
}

data class SearchScreenState(
    val query: String = "",
    val characters: List<Character> = emptyList()
)

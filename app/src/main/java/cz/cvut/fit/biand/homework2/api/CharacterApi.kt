package cz.cvut.fit.biand.homework2.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import android.util.Log
import cz.cvut.fit.biand.homework2.data.CharacterDto
import io.ktor.http.isSuccess

class CharacterApi(private val client: HttpClient) {
    suspend fun getCharacters(): List<CharacterDto> {
        return try {
            val response: CharacterResponse = client.get("https://rickandmortyapi.com/api/character").body()
            Log.d("API", "Response: $response")
            response.results
        } catch (e: Exception) {
            Log.e("API", "Error: ${e.localizedMessage}", e)
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getCharacterById(id: Int): CharacterDto? {
        return try {
            client.get("https://rickandmortyapi.com/api/character/$id").body()
        } catch (e: Exception) {
            Log.e("API", "Error fetching character $id: ${e.localizedMessage}")
            null
        }
    }

    suspend fun searchCharactersByName(query: String): List<CharacterDto> {
        return try {
            val response = client.get("https://rickandmortyapi.com/api/character/") {
                parameter("name", query)
            }

            if (response.status.isSuccess()) {
                response.body<CharacterResponse>().results
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("API", "Search error: ${e.localizedMessage}", e)
            emptyList()
        }
    }
}

@Serializable
data class CharacterResponse(
    val results: List<CharacterDto>
)

package cz.cvut.fit.biand.homework2.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDAO {
    @Query("SELECT * FROM CharacterEntity")
    suspend fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM CharacterEntity WHERE id = :id")
    suspend fun getById(id: Int): CharacterEntity?

    // method using Flow
    @Query("SELECT * FROM CharacterEntity WHERE id = :id")
    fun getCharacterFlowById(id: Int): Flow<CharacterEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Query("SELECT * FROM CharacterEntity WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%'")
    suspend fun searchCharactersByName(query: String): List<CharacterEntity>

    @Query("SELECT * FROM CharacterEntity WHERE isFavorite = 1")
    fun getFavoritesFlow(): Flow<List<CharacterEntity>>

    @Query("UPDATE CharacterEntity SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

    @Query("SELECT * FROM CharacterEntity")
    fun getAllFlow(): Flow<List<CharacterEntity>>
}

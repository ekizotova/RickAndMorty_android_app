package cz.cvut.fit.biand.homework2.data

import androidx.room.*

// smells like springboot
@Entity
data class CharacterEntity(
    @PrimaryKey val id: Int,

    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
    val type: String,
    val origin: String,
    val location: String,
    val isFavorite: Boolean = false,
)

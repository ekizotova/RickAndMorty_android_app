package cz.cvut.fit.biand.homework2.data

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginDto,
    val location: LocationDto,
    val image: String,
)

@Serializable
data class OriginDto(
    val name: String
)

@Serializable
data class LocationDto(
    val name: String
)

fun CharacterDto.toEntity(): CharacterEntity = CharacterEntity(
    id = this.id,
    name = this. name,
    status = this.status,
    species = this.species,
    gender = this.gender,
    image = this.image,
    type = this.type,
    origin = this.origin.name,
    location = this.location.name,
)

fun CharacterEntity.toDomain() = Character(
    id = id.toLong(),
    name = name,
    status = enumValueOf<Character.Status>(status),
    species = species,
    gender = enumValueOf<Character.Gender>(gender),
    imageUrl = image,
    type = type,
    origin = origin,
    location = location,
    isFavorite = this.isFavorite,
)

fun CharacterDto.toDomain(): Character {
    return Character(
        id = id.toLong(),
        name = name,
        status = enumValueOf<Character.Status>(status),
        species = species,
        type = type,
        gender = enumValueOf<Character.Gender>(gender),
        origin = origin.name,
        location = location.name,
        imageUrl = image,
        isFavorite = false,
    )
}

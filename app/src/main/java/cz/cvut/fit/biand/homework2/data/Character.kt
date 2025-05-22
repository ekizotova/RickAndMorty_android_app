package cz.cvut.fit.biand.homework2.data

data class Character(
    val id: Long,
    val name: String,
    val status: Status,
    val species: String,
    val type: String = "",
    val gender: Gender,
    val origin: String = "",
    val location: String = "",
    val imageUrl: String,
    val isFavorite: Boolean,
) {

    enum class Status {
        Dead,
        Alive,
        unknown;
    }

    enum class Gender {
        Female, Male, Genderless, unknown
    }
}

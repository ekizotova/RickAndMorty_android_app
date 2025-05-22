package cz.cvut.fit.biand.homework2.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import cz.cvut.fit.biand.homework2.R
import kotlinx.serialization.Serializable

sealed interface Screens {

    sealed interface TopLevel : Screens {
        @get:DrawableRes
        val icon: Int

        @get:StringRes
        val title: Int

        @Serializable
        data object Characters : TopLevel {
            override val title: Int
                get() = R.string.characters

            override val icon: Int
                get() = R.drawable.ic_characters
        }

        @Serializable
        data object Favorites : TopLevel {
            override val title: Int
                get() = R.string.favorites

            override val icon: Int
                get() = R.drawable.ic_favorites_filled
        }

        companion object {
            val all get() = listOf(Characters, Favorites)
        }
    }

    @Serializable
    data object Search : Screens

    @Serializable
    data class CharacterDetail(val id: Long) : Screens
}

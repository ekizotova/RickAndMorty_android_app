package cz.cvut.fit.biand.homework2.di

import android.content.Context
import androidx.room.Room
import cz.cvut.fit.biand.homework2.config.AppDatabase
import cz.cvut.fit.biand.homework2.data.CharacterDAO
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "RickAndMortyAppDatabase"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideCharacterDao(database: AppDatabase): CharacterDAO {
        return database.characterDao()
    }
}

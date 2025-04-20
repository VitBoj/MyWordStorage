package com.example.mywordstorage.di

import android.content.Context
import androidx.room.Room
import com.example.mywordstorage.data.local.AppDatabase
import com.example.mywordstorage.data.local.DatabaseConstants.DATABASE_NAME
import com.example.mywordstorage.data.local.DatabaseConstants.DATABASE_PATH
import com.example.mywordstorage.data.local.dao.CategoryDao
import com.example.mywordstorage.data.local.dao.WordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideWordDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
            /* Preload the database with a default category */
        ).createFromAsset(DATABASE_PATH).build()
    }

    @Provides
    fun provideWordDao(database: AppDatabase): WordDao {
        return database.wordDao()
    }

    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }
}

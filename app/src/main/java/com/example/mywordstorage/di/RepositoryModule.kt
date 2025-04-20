package com.example.mywordstorage.di

import com.example.mywordstorage.data.local.dao.CategoryDao
import com.example.mywordstorage.data.local.dao.WordDao
import com.example.mywordstorage.data.repository.CategoryRepositoryImpl
import com.example.mywordstorage.data.repository.WordRepositoryImpl
import com.example.mywordstorage.domain.repository.CategoryRepository
import com.example.mywordstorage.domain.repository.WordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideWordRepository(wordDao: WordDao): WordRepository {
        return WordRepositoryImpl(wordDao)
    }

    @Provides
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository {
        return CategoryRepositoryImpl(categoryDao)
    }
}
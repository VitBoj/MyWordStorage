package com.example.mywordstorage.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mywordstorage.data.local.dao.CategoryDao
import com.example.mywordstorage.data.local.dao.WordDao
import com.example.mywordstorage.data.local.entities.CategoryEntity
import com.example.mywordstorage.data.local.entities.WordEntity

@Database(entities = [WordEntity::class, CategoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun categoryDao(): CategoryDao
}

object DatabaseConstants {
    const val DATABASE_NAME = "word_database"
    const val DATABASE_PATH = "database/database.db"
    const val WORDS_TABLE = "words"
    const val CATEGORIES_TABLE = "categories"
}
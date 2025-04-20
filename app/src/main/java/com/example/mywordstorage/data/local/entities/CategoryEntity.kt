package com.example.mywordstorage.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mywordstorage.data.local.DatabaseConstants.CATEGORIES_TABLE

@Entity(tableName = CATEGORIES_TABLE)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)
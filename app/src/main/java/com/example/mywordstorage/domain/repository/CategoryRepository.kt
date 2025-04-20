package com.example.mywordstorage.domain.repository

import com.example.mywordstorage.data.local.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun addCategory(category: CategoryEntity):Long
    fun getAllCategories(): Flow<List<CategoryEntity>>
    suspend fun getCategoryById(id: Long): CategoryEntity
    suspend fun deleteCategory(id: Long)
    suspend fun updateCategoryName(categoryId: Long, newName: String)
}
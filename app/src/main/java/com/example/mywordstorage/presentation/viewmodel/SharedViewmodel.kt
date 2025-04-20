package com.example.mywordstorage.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywordstorage.data.local.entities.CategoryEntity
import com.example.mywordstorage.data.local.entities.WordEntity
import com.example.mywordstorage.domain.repository.CategoryRepository
import com.example.mywordstorage.domain.repository.WordRepository
import com.example.mywordstorage.utils.ClipboardHelper
import com.example.mywordstorage.utils.DEFAULT_CATEGORY_ID
import com.example.mywordstorage.utils.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SharedViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val categoryRepository: CategoryRepository,
    private val wordRepository: WordRepository
) : ViewModel() {
    // Theme
    private val _isDarkTheme = MutableStateFlow<Boolean?>(false)
    val isDarkTheme: StateFlow<Boolean?> = _isDarkTheme

    // Categories
    val categories: StateFlow<List<CategoryEntity>> = categoryRepository
        .getAllCategories()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    private val _selectedCategory = MutableStateFlow<CategoryEntity?>(null)
    val selectedCategory: StateFlow<CategoryEntity?> = _selectedCategory

    // Search
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // Filter
    private val _showJustUnlearned = MutableStateFlow(false)
    val showJustUnlearned: StateFlow<Boolean> = _showJustUnlearned

    // Word selection
    private val _selectedWords = MutableStateFlow<Set<Long>>(emptySet())
    val selectedWords: StateFlow<Set<Long>> = _selectedWords

    // Filtered words
    val filteredWords = combine(
        _selectedCategory.filterNotNull().flatMapLatest { category ->
            wordRepository.getWordsByCategory(category.id)
        },
        searchQuery,
        _showJustUnlearned
    ) { words, query, showUnlearned ->
        words.filter {
            (it.word.contains(query, true) || it.translation.contains(query, true)) &&
                    (!showUnlearned || !it.isLearned)
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        viewModelScope.launch {
            _isDarkTheme.value = preferencesManager.isDarkTheme()
            _selectedCategory.value = getSelectedCategoryFromPreferences()
        }
    }

    // --- Theme ---
    fun setDarkTheme(enabled: Boolean) {
        viewModelScope.launch {
            _isDarkTheme.value = enabled
            preferencesManager.setDarkTheme(enabled)
        }
    }

    // --- Search ---
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setShowJustUnlearned(enabled: Boolean) {
        _showJustUnlearned.value = enabled
    }

    // --- Words ---
    fun getWordById(id: Long): Flow<WordEntity?> =
        wordRepository.getWordById(id)

    fun addWord(word: WordEntity) {
        viewModelScope.launch {
            wordRepository.addWord(word)
        }
    }

    fun updateWord(word: WordEntity) {
        viewModelScope.launch {
            wordRepository.updateWord(word)
        }
    }

    fun deleteWord(id: Long) {
        viewModelScope.launch {
            wordRepository.deleteWord(id)
        }
    }

    fun markWordAsLearned(word: WordEntity, isLearned: Boolean) {
        viewModelScope.launch {
            updateWord(word.copy(isLearned = isLearned))
        }
    }

    fun deleteSelectedWords() {
        viewModelScope.launch {
            selectedWords.value.forEach { id ->
                wordRepository.deleteWord(id)
            }
            _selectedWords.value = emptySet()
        }
    }

    fun toggleWordSelection(id: Long) {
        _selectedWords.value = if (_selectedWords.value.contains(id)) {
            _selectedWords.value - id
        } else {
            _selectedWords.value + id
        }
    }

    fun selectAllWords() {
        _selectedWords.value = filteredWords.value.map { it.id }.toSet()
    }

    fun clearSelectedWords() {
        _selectedWords.value = emptySet()
    }

    fun copySelectedWordsToClipboard(context: Context) {
        val words = filteredWords.value.filter { selectedWords.value.contains(it.id) }
        ClipboardHelper.copyToClipboard(words, context)
    }

    // --- Categories ---
    private suspend fun setSelectedCategory(categoryId: Long) {
        _selectedCategory.value = categoryRepository.getCategoryById(categoryId)
        saveSelectedCategoryToPreferences(categoryId)
    }

    fun selectCategory(categoryId: Long) {
        viewModelScope.launch {
            setSelectedCategory(categoryId)
        }
    }

    fun addCategory(category: CategoryEntity) {
        viewModelScope.launch {
            val cat = categoryRepository.addCategory(category)
            setSelectedCategory(cat)
        }
    }

    fun updateCategory(categoryId: Long, newName: String) {
        viewModelScope.launch {
            categoryRepository.updateCategoryName(categoryId, newName)
            setSelectedCategory(categoryId)
        }
    }

    fun deleteCategory(catId: Long) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(catId)
            setSelectedCategory(DEFAULT_CATEGORY_ID)
        }
    }

    // --- Preferences ---
    private suspend fun saveSelectedCategoryToPreferences(catId: Long) {
        preferencesManager.saveSelectedCategory(catId)
    }

    private suspend fun getSelectedCategoryFromPreferences(): CategoryEntity {
        return categoryRepository.getCategoryById(preferencesManager.getSelectedCategory())
    }
}




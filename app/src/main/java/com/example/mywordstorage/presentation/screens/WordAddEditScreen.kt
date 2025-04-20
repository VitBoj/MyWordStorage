package com.example.mywordstorage.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mywordstorage.data.local.entities.WordEntity
import com.example.mywordstorage.presentation.components.dropdown.CategoryDropdown
import com.example.mywordstorage.presentation.components.FormField
import com.example.mywordstorage.presentation.ui.theme.AppTheme
import com.example.mywordstorage.presentation.ui.theme.AppTypography
import com.example.mywordstorage.presentation.ui.theme.Turquoise
import com.example.mywordstorage.presentation.viewmodel.SharedViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun WordAddEditScreen(
    wordId: Long?,
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val word by remember(wordId) {
        wordId?.let { sharedViewModel.getWordById(it) } ?: flowOf(null)
    }.collectAsState(initial = null)
    var wordText by rememberSaveable { mutableStateOf("") }
    var translationText by rememberSaveable { mutableStateOf("") }
    var transcriptionText by rememberSaveable { mutableStateOf("") }
    var exampleText by rememberSaveable { mutableStateOf("") }
    var isLearned by rememberSaveable { mutableStateOf(false) }
    val categories by sharedViewModel.categories.collectAsState(initial = emptyList())
    var categoryId by rememberSaveable {
        mutableLongStateOf(
            word?.categoryId ?: sharedViewModel.selectedCategory.value!!.id
        )
    }
    val selectedCategory = categories.find { it.id == categoryId }

    LaunchedEffect(word) {
        word?.let {
            wordText = it.word
            translationText = it.translation
            transcriptionText = it.transcription
            exampleText = it.example
            isLearned = it.isLearned
            categoryId = it.categoryId
        }
    }

    fun saveWord() {
        val common = WordEntity(
            word = wordText.trim(),
            translation = translationText.trim(),
            transcription = transcriptionText.trim(),
            example = exampleText.trim(),
            categoryId = categoryId,
            isLearned = isLearned
        )
        if (word != null) {
            sharedViewModel.updateWord(common.copy(id = word!!.id))
        } else {
            sharedViewModel.addWord(common)
        }
        navController.popBackStack()
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(AppTheme.colors.backgroundColor)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .padding()
                .fillMaxSize()
                .padding(16.dp)
                .background(AppTheme.colors.backgroundColor)
        ) {
            Text(
                text = if (word == null) "Add New Word" else "Edit Word",
                color = AppTheme.colors.textColor,
                style = AppTypography.titleMedium
            )
            Spacer(modifier = Modifier.height(15.dp))
            FormField(wordText, { wordText = it }, "Word")
            Spacer(modifier = Modifier.height(8.dp))
            FormField(translationText, { translationText = it }, "Translation")
            Spacer(modifier = Modifier.height(8.dp))
            FormField(transcriptionText, { transcriptionText = it }, "Transcription")
            Spacer(modifier = Modifier.height(8.dp))
            FormField(exampleText, { exampleText = it }, "Example")
            Spacer(modifier = Modifier.height(8.dp))
            CategoryDropdown(categories, selectedCategory) { id -> categoryId = id }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Turquoise),
                onClick = { saveWord() },
                enabled = wordText.isNotBlank() || translationText.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    if (word == null) "Add New Word" else "Save Changes",
                    color = Color.White,
                    style = AppTypography.bodySmall
                )
            }
        }
    }
}

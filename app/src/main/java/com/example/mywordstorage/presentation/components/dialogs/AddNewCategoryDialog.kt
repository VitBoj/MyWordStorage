package com.example.mywordstorage.presentation.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.mywordstorage.data.local.entities.CategoryEntity
import com.example.mywordstorage.presentation.components.FormField
import com.example.mywordstorage.presentation.ui.theme.AppTheme
import com.example.mywordstorage.presentation.ui.theme.Turquoise

@Composable
fun AddNewCategoryDialog(
    onDismiss: () -> Unit,
    onAdd: (category: CategoryEntity) -> Unit
) {
    var newCategoryName by rememberSaveable { mutableStateOf("") }
    AlertDialog(
        containerColor = AppTheme.colors.backgroundColor,
        onDismissRequest = onDismiss,
        title = { Text("Add category", color = AppTheme.colors.textColor) },
        text = {
            FormField(
                text = newCategoryName,
                onValueChange = { newCategoryName = it },
                placeholder = "Category name"
            )
        },
        confirmButton = {
            val isCategoryFieldEmpty = newCategoryName.isBlank()
            TextButton(onClick = {
                onAdd(CategoryEntity(name = newCategoryName.trim()))
                onDismiss()
            }, enabled = !isCategoryFieldEmpty) {
                Text("Add", color = if (!isCategoryFieldEmpty) Turquoise else Color.Gray)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = AppTheme.colors.textColor)
            }
        })
}
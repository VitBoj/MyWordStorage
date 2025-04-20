package com.example.mywordstorage.presentation.components.dropdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mywordstorage.data.local.entities.CategoryEntity
import com.example.mywordstorage.presentation.ui.theme.AppTheme
import com.example.mywordstorage.presentation.ui.theme.AppTypography

@Composable
fun CategoryDropdown(
    categories: List<CategoryEntity>,
    selectedCategory: CategoryEntity?,
    setCategoryId: (Long) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(onClick = { expanded = true }) {
            Text(
                text = selectedCategory?.name ?: "",
                color = AppTheme.colors.textColor,
                style = AppTypography.bodySmall,
                modifier = Modifier.widthIn(max = 140.dp),
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open menu",
                tint = AppTheme.colors.textColor
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = AppTheme.colors.menuColor
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Text(
                            category.name,
                            color = Color.DarkGray,
                            style = AppTypography.bodySmall
                        )
                    },
                    onClick = {
                        setCategoryId(category.id)
                        expanded = false
                    }
                )
            }
        }
    }
}

package com.example.mywordstorage.presentation.components.dropdown

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mywordstorage.data.local.entities.CategoryEntity
import com.example.mywordstorage.presentation.components.dialogs.AddNewCategoryDialog
import com.example.mywordstorage.presentation.components.dialogs.CategoryEditDialog
import com.example.mywordstorage.presentation.ui.theme.AppTheme
import com.example.mywordstorage.presentation.ui.theme.AppTypography
import com.example.mywordstorage.utils.DEFAULT_CATEGORY_ID

@Composable
fun CategoryDropdownMenu(
    selectedCategory: CategoryEntity?,
    categories: List<CategoryEntity>,
    onItemClick: (CategoryEntity) -> Unit,
    onAdd: (CategoryEntity) -> Unit,
    onDelete: (Long) -> Unit,
    onUpdate: (Long, String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showAddCategoryDialog by remember { mutableStateOf(false) }
    var showEditCategoryDialog by remember { mutableStateOf(false) }
    val isDefaultCategory = selectedCategory?.id == DEFAULT_CATEGORY_ID

    Row{
        TextButton(
            onClick = { expanded = true },
            shape = RectangleShape,
            modifier = Modifier.padding(0.dp).widthIn(max = 140.dp),
            contentPadding = PaddingValues(0.dp),

        ) {
            Text(
                text =  selectedCategory?.name ?: "",
                color = AppTheme.colors.textColor,
                style = AppTypography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "",
                tint = AppTheme.colors.textColor
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = AppTheme.colors.menuColor
        ) {
            Column {
                TextButton(
                    onClick = {
                        showEditCategoryDialog = true
                        expanded = false
                    },
                    modifier = Modifier.padding(0.dp),
                    enabled = !isDefaultCategory
                ) {
                    Text(
                        text = "Edit current category",
                        color = if (isDefaultCategory) Color.Gray else Color.DarkGray,
                        style = AppTypography.bodySmall
                    )
                }
                TextButton(
                    onClick = {
                        showAddCategoryDialog = true
                        expanded = false
                    },
                    modifier = Modifier.padding(0.dp)
                ) {
                    Text(
                        text = "Add new category",
                        color = Color.DarkGray,
                        style = AppTypography.bodySmall,
                    )
                }
                HorizontalDivider(thickness = 1.dp, color = AppTheme.colors.backgroundColor)
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = category.name,
                                color = Color.DarkGray,
                                style = AppTypography.bodySmall,
                            )
                        },
                        onClick = {
                            onItemClick(category)
                        }
                    )
                }
            }
        }
    }
    if (showAddCategoryDialog) {
        AddNewCategoryDialog(
            onDismiss = { showAddCategoryDialog = false },
            onAdd = { cat -> onAdd(cat) }
        )
    }
    if (showEditCategoryDialog) {
        CategoryEditDialog(
            category = selectedCategory!!,
            onDismiss = { showEditCategoryDialog = false },
            onDelete = {
                onDelete(selectedCategory.id)
                showEditCategoryDialog = false
            },
            onEdit = { newCategoryName ->
                onUpdate(selectedCategory.id, newCategoryName)
                showEditCategoryDialog = false
            }
        )
    }
}

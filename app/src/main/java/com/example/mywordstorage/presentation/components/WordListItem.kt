package com.example.mywordstorage.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mywordstorage.data.local.entities.WordEntity
import com.example.mywordstorage.presentation.ui.theme.AppTheme
import com.example.mywordstorage.presentation.ui.theme.AppTypography
import com.example.mywordstorage.presentation.ui.theme.Orange
import com.example.mywordstorage.presentation.ui.theme.Red
import com.example.mywordstorage.presentation.ui.theme.Turquoise

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WordListItem(
    word: WordEntity,
    onDelete: () -> Unit,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    // Dismiss item if swiped over 70% of its width
    val threshold = 0.7f
    val swipeState = rememberSwipeToDismissBoxState(
        positionalThreshold = { it * threshold },
    )
    val (icon, alignment, color) = when (swipeState.dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> Triple(Icons.Outlined.Delete, Alignment.CenterEnd, Red)
        SwipeToDismissBoxValue.StartToEnd -> Triple(
            Icons.Outlined.Edit,
            Alignment.CenterStart,
            Turquoise
        )
        else -> Triple(Icons.Outlined.Delete, Alignment.CenterEnd, Red)
    }

    SwipeToDismissBox(
        modifier = Modifier
            .animateContentSize()
            .height(80.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        state = swipeState,
        //Item background content
        backgroundContent = {
            Box(
                contentAlignment = alignment,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    ) {
        //Item foreground content
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.backgroundColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(5.dp)
                    .background(if (word.isLearned) Turquoise else Red)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = word.word,
                    color = AppTheme.colors.textColor,
                    style = AppTypography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = word.translation,
                    color = Color.Gray,
                    style = AppTypography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(10.dp)
                    .background(if (isSelected) Orange else AppTheme.colors.backgroundColor)
            )
        }
    }

    LaunchedEffect(swipeState.currentValue) {
        when (swipeState.currentValue) {
            SwipeToDismissBoxValue.EndToStart -> {
                onDelete()
                swipeState.snapTo(SwipeToDismissBoxValue.Settled)
            }

            SwipeToDismissBoxValue.StartToEnd -> {
                onClick()
                swipeState.snapTo(SwipeToDismissBoxValue.Settled)
            }
            else -> Unit
        }
    }
}
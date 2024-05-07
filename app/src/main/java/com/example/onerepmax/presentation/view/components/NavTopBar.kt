package com.example.onerepmax.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.onerepmax.R
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavTopBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    actions: @Composable () -> Unit = {}
) {
    val barColors = topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.secondary
    )

    val icon = if (canNavigateBack) Icons.AutoMirrored.Filled.ArrowBack
        else Icons.Filled.Menu

    TopAppBar(
        title = {
            Text(text = title, color = MaterialTheme.colorScheme.secondary)
        },
        actions = { actions() },
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(R.string.top_bar_icon),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        },
        colors = barColors
    )

}
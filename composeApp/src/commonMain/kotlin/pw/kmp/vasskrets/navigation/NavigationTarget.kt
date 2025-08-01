package pw.kmp.vasskrets.navigation

import androidx.compose.runtime.Composable

data class NavigationTarget(
    val id: String,
    val label: String,
    val icon: @Composable (() -> Unit),
    val onClick: () -> Unit,
    val isSelected: Boolean
)

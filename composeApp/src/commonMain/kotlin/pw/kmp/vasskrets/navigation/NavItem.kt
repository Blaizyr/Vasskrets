package pw.kmp.vasskrets.navigation

import androidx.compose.runtime.Composable

data class NavItem(
    val id: String,
    val label: String,
    val icon: @Composable (() -> Unit),
    val onClick: () -> Unit,
    val isSelected: Boolean
)

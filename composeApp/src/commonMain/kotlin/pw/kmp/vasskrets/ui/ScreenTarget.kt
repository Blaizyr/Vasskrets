package pw.kmp.vasskrets.ui

import pw.kmp.vasskrets.components.root.Child
import pw.kmp.vasskrets.navigation.NavigationConfig

sealed class ScreenTarget {
    data object Home : ScreenTarget()
    data object Settings : ScreenTarget()
    data object Profile : ScreenTarget()
    data object Notes : ScreenTarget()
    data object Conversations : ScreenTarget()
}

fun ScreenTarget.toNavigationConfig(): NavigationConfig = when(this) {
    ScreenTarget.Home -> NavigationConfig.Home
    ScreenTarget.Notes -> NavigationConfig.Notes
    ScreenTarget.Conversations -> NavigationConfig.Conversations
    ScreenTarget.Profile -> NavigationConfig.Profile
    ScreenTarget.Settings -> NavigationConfig.Settings
}

fun resolveTarget(child: Child): ScreenTarget = when(child) {
    is Child.Home -> ScreenTarget.Home
    is Child.Notes -> ScreenTarget.Notes
    is Child.Conversations -> ScreenTarget.Conversations
    is Child.Settings -> ScreenTarget.Settings
    is Child.Profile -> ScreenTarget.Profile
}
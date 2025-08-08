package pw.kmp.vasskrets.ui

import pw.kmp.vasskrets.components.root.Child
import pw.kmp.vasskrets.navigation.MainNavigationConfig

sealed class ScreenTarget {
    data object Home : ScreenTarget()
    data object Settings : ScreenTarget()
    data object Profile : ScreenTarget()
    data object Notes : ScreenTarget()
    data object Conversations : ScreenTarget()
}

fun ScreenTarget.toNavigationConfig(): MainNavigationConfig = when(this) {
    ScreenTarget.Home -> MainNavigationConfig.Home
    ScreenTarget.Notes -> MainNavigationConfig.Notes
    ScreenTarget.Conversations -> MainNavigationConfig.Conversations
    ScreenTarget.Profile -> MainNavigationConfig.Profile
    ScreenTarget.Settings -> MainNavigationConfig.Settings
}

fun resolveTarget(child: Child): ScreenTarget = when(child) {
    is Child.Home -> ScreenTarget.Home
    is Child.Notes -> ScreenTarget.Notes
    is Child.Conversations -> ScreenTarget.Conversations
    is Child.Settings -> ScreenTarget.Settings
    is Child.Profile -> ScreenTarget.Profile
}
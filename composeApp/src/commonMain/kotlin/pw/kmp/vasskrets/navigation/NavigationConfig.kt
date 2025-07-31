package pw.kmp.vasskrets.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationConfig {
    @Serializable
    data object Home : NavigationConfig()

    @Serializable
    data object Notes : NavigationConfig()

    @Serializable
    data object Profile : NavigationConfig()

    @Serializable
    data object Conversations : NavigationConfig()

    @Serializable
    data object Settings : NavigationConfig()

}
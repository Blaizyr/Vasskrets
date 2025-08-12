package pw.kmp.vasskrets.navigation.main

import kotlinx.serialization.Serializable

@Serializable
sealed class MainNavigationConfig {
    @Serializable
    data object Home : MainNavigationConfig()

    @Serializable
    data object Notes : MainNavigationConfig()

    @Serializable
    data object Profile : MainNavigationConfig()

    @Serializable
    data object Conversations : MainNavigationConfig()

    @Serializable
    data object Settings : MainNavigationConfig()

}
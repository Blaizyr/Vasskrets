package pw.kmp.vasskrets.components.root

import kotlinx.serialization.Serializable

@Serializable
sealed class Child {
    @Serializable
    data object Login : Child()

    @Serializable
    data object Home : Child()

    @Serializable
    data object Notes : Child()

    @Serializable
    data object Conversations : Child()

}

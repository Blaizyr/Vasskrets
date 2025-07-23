package pw.kmp.vasskrets.components.root

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
sealed class Child {
    @Serializable
    data object Login : Child()

    @Serializable
    data object Home : Child()

    @Serializable
    data object Notes : Child()

    @OptIn(ExperimentalUuidApi::class)
    @Serializable
    data class Conversation(val conversationId: Uuid) : Child()
}

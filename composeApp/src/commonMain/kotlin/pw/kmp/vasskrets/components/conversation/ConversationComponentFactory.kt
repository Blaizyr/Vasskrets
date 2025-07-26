package pw.kmp.vasskrets.components.conversation

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun interface ConversationComponentFactory {
    operator fun invoke(conversationId: Uuid): ConversationComponent
}
package pw.kmp.vasskrets.components.conversation

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun interface ConversationComponentFactory {
    fun create(conversationId: Uuid): ConversationComponent
}
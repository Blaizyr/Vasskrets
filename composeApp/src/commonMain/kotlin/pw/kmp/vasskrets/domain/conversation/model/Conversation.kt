package pw.kmp.vasskrets.domain.conversation.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Conversation(
    val id: Uuid = Uuid.random(),
    val messages: List<ConversationMessage> = emptyList(),
)

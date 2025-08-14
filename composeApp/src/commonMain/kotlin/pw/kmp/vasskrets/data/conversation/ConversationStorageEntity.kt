package pw.kmp.vasskrets.data.conversation

import kotlinx.serialization.Serializable
import pw.kmp.vasskrets.domain.conversation.model.ConversationMessage
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class ConversationStorageEntity(
    val id: Uuid,
    val messages: List<ConversationMessage>,
)

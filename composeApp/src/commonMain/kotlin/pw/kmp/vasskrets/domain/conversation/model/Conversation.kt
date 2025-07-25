package pw.kmp.vasskrets.domain.conversation.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
@Serializable
data class Conversation(
    val id: Uuid = Uuid.random(),
    val name: String? = null,
    @Contextual val beginsAt: Instant,
    val tags: List<String> = emptyList(),
    val messages: List<ConversationMessage>,
    val meta: Map<String, String> = emptyMap()
)

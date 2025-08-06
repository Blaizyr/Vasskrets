package pw.kmp.vasskrets.domain.conversation.model

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
@OptIn(ExperimentalUuidApi::class)
data class ConversationDestinationConfig(
    val configUuid: Uuid = Uuid.random(),
    val conversationUuid: Uuid,
)

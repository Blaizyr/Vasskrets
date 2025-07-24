package pw.kmp.vasskrets.domain.conversation.model

import pw.kmp.vasskrets.components.conversation.ConversationComponent
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class ConversationEntry(
    val id: Uuid,
    val component: ConversationComponent
)

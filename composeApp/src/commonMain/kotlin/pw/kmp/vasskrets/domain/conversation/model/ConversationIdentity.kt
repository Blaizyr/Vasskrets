package pw.kmp.vasskrets.domain.conversation.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class ConversationIdentity(val id: Uuid, val metadata: ConversationMetadata)

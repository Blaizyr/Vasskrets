package pw.kmp.vasskrets.domain.conversation.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
@Serializable
data class ConversationMessage(
    val id: Uuid = Uuid.random(),
    val text: String,
    val participant: Participant,
    @Contextual val sentAt: Instant,
)

package pw.kmp.vasskrets.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
@Serializable
data class ChatMessage(
    val id: Uuid = Uuid.random(),
    val text: String,
    val sender: ChatSender,
    @Contextual val sentAt: Instant,
)

package pw.kmp.vasskrets.domain.conversation.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import pw.kmp.vasskrets.domain.Metadata
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
@Serializable
data class ConversationMetadata(
    override val id: Uuid,
    @Contextual override val lastModified: Instant,
    val title: String?,
) : Metadata
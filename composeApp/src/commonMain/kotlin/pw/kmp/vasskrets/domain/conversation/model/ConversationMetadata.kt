package pw.kmp.vasskrets.domain.conversation.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import pw.kmp.vasskrets.domain.Metadata
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Serializable
data class ConversationMetadata(
    override val id: String,
    @Contextual override val lastModified: Instant,
) : Metadata
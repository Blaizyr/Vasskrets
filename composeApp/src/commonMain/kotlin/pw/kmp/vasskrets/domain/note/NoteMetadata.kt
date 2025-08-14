package pw.kmp.vasskrets.domain.note

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import pw.kmp.vasskrets.domain.Metadata
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
@Serializable
data class NoteMetadata(
    override val id: Uuid,
    @Contextual
    val lastModified: Instant,
    override val tags: List<String> = emptyList(),
    @Contextual
    override val createdAt: Instant = Clock.System.now(),
    val priority: Int?,
) : Metadata

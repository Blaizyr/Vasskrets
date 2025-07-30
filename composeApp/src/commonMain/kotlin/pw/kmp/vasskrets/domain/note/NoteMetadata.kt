package pw.kmp.vasskrets.domain.note

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import pw.kmp.vasskrets.domain.Metadata
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
@Serializable
data class NoteMetadata(
    override val id: Uuid,
    @Contextual override val lastModified: Instant
) : Metadata

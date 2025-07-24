package pw.kmp.vasskrets.domain.note

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import pw.kmp.vasskrets.domain.Metadata
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Serializable
data class NoteMetadata(
    override val id: String,
    @Contextual override val lastModified: Instant
) : Metadata

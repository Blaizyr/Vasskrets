package pw.kmp.vasskrets.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Serializable
data class NoteMetadata(
    override val id: String,
    @Contextual override val lastModified: Instant
) : Metadata

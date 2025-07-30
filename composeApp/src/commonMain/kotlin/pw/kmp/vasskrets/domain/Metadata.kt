package pw.kmp.vasskrets.domain

import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
interface Metadata {
    val id: Uuid
    val lastModified: Instant
}

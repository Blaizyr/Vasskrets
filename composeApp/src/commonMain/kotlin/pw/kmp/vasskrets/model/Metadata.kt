package pw.kmp.vasskrets.model

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
interface Metadata {
    val id: String
    val lastModified: Instant
}

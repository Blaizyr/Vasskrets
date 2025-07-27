package pw.kmp.vasskrets

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class Session(
    val id: String,
    val userId: String,
    val roles: List<String>,
    val expiresAt: Instant? = null,
)

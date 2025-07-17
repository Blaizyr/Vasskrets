package pw.kmp.vasskrets.model

import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class Note(
    val id: String = Uuid.random().toString(),
    val title: String,
    val content: String,
    val tags: List<String> = emptyList(),
    val relations: List<Relation> = emptyList(),
    val meta: NoteMeta = NoteMeta()
)

@Serializable
data class Relation(val type: String, val targetId: String)

@OptIn(ExperimentalTime::class)
@Serializable
data class NoteMeta(
    val created: String = Clock.System.now().toString(),
    val priority: Int = 3,
    val context: String = ""
)
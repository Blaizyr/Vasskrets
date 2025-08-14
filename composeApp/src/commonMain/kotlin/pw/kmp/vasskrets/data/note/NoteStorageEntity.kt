package pw.kmp.vasskrets.data.note

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class NoteStorageEntity(
    val id: Uuid,
    val title: String,
    val content: String,
)

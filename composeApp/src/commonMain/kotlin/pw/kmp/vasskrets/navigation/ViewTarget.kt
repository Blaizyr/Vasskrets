package pw.kmp.vasskrets.navigation

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
sealed class ViewTarget {
    data class Conversation(val conversationId: Uuid) : ViewTarget()
    data class Note(val noteId: Uuid) : ViewTarget()
}

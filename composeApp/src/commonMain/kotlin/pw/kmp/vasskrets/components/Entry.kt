package pw.kmp.vasskrets.components

import pw.kmp.vasskrets.components.conversation.ConversationComponent
import pw.kmp.vasskrets.components.notes.NotesComponent
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
sealed class Entry<out T> {
    abstract val componentId: Uuid
    abstract val component: T

    data class ConversationEntry(
        override val componentId: Uuid,
        override val component: ConversationComponent,
    ): Entry<ConversationComponent>()

    data class NoteEntry(
        override val componentId: Uuid,
        override val component: NotesComponent,
    ): Entry<NotesComponent>()
}

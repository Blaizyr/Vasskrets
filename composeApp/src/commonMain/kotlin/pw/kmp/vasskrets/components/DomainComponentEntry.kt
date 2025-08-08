package pw.kmp.vasskrets.components

import kotlinx.serialization.Serializable
import pw.kmp.vasskrets.components.conversation.ConversationComponent
import pw.kmp.vasskrets.components.notes.NotesComponent
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
sealed class DomainComponentEntry<out T> {
    abstract val componentId: Uuid
    abstract val component: T

    @Serializable
    data class ConversationEntry(
        override val componentId: Uuid,
        override val component: ConversationComponent,
    ) : DomainComponentEntry<ConversationComponent>()

    @Serializable
    data class NoteEntry(
        override val componentId: Uuid,
        override val component: NotesComponent,
    ) : DomainComponentEntry<NotesComponent>()
}

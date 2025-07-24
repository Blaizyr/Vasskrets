package pw.kmp.vasskrets.domain

import kotlinx.serialization.KSerializer
import pw.kmp.vasskrets.domain.conversation.model.ConversationMetadata
import pw.kmp.vasskrets.domain.note.NoteMetadata

sealed interface DataType<T : Metadata> {
    val subDir: String
    val metadataDeserializer: KSerializer<T>
}

data object ChatType : DataType<ConversationMetadata> {
    override val subDir = "chats"
    override val metadataDeserializer: KSerializer<ConversationMetadata> = ConversationMetadata.serializer()
}

data object NoteType : DataType<NoteMetadata> {
    override val subDir = "notes"
    override val metadataDeserializer: KSerializer<NoteMetadata> = NoteMetadata.serializer()
}

package pw.kmp.vasskrets.data

import pw.kmp.vasskrets.domain.Metadata
import pw.kmp.vasskrets.domain.conversation.model.ConversationMetadata
import pw.kmp.vasskrets.domain.note.NoteMetadata

sealed interface MetadataType<T : Metadata>

data object ConversationMetadataType : MetadataType<ConversationMetadata>
data object NoteMetadataType : MetadataType<NoteMetadata>

package pw.kmp.vasskrets.domain.model

import kotlinx.serialization.KSerializer

sealed interface DataType<T : Metadata> {
    val subDir: String
    val metadataDeserializer: KSerializer<T>
}

data object ChatType : DataType<ChatMetadata> {
    override val subDir = "chats"
    override val metadataDeserializer: KSerializer<ChatMetadata> = ChatMetadata.serializer()
}

data object NoteType : DataType<NoteMetadata> {
    override val subDir = "notes"
    override val metadataDeserializer: KSerializer<NoteMetadata> = NoteMetadata.serializer()
}

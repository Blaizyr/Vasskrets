package pw.kmp.vasskrets.data.conversation

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import pw.kmp.vasskrets.data.StorageConfig

@Serializable
data object ConversationStorageConfig : StorageConfig<ConversationStorageEntity> {
    override val subDir: String = "conversations"
    override val serializer: KSerializer<ConversationStorageEntity> =
        ConversationStorageEntity.serializer()
}

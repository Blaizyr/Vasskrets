package pw.kmp.vasskrets.data.note

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import pw.kmp.vasskrets.data.StorageConfig

@Serializable
data object NoteStorageConfig : StorageConfig<NoteStorageEntity> {
    override val subDir: String = "notes"
    override val serializer: KSerializer<NoteStorageEntity> = NoteStorageEntity.serializer()
}

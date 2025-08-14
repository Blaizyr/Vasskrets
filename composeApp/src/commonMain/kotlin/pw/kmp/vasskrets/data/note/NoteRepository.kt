package pw.kmp.vasskrets.data.note

import kotlinx.serialization.KSerializer
import okio.Path
import okio.Path.Companion.toPath
import pw.kmp.vasskrets.data.JsonStorage
import pw.kmp.vasskrets.domain.note.Note
import pw.kmp.vasskrets.platform.provideBaseDir
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
object NoteRepository {
    // TODO implement data sources #14,
    // TODO change stored type to NoteStorageEntity #15
    private val storage: JsonStorage = JsonStorage()
    private val serializer: KSerializer<Note> = Note.serializer()
    private val noteDir: String = "${provideBaseDir()}/notes"

    private fun noteFilePath(noteId: String): Path = "$noteDir/$noteId.json".toPath()

    fun save(note: Note): Boolean =
        storage.save(noteFilePath(note.id.toString()).name, note, serializer)

    fun saveAndPoint(note: Note): Path? {
        val saveSucceeded = save(note)
        return if (saveSucceeded) noteFilePath(note.id.toString()) else null
    }

    fun get(noteId: String): Note? = storage.load(noteFilePath(noteId), serializer)

    fun delete(noteId: String): Boolean = storage.delete(noteFilePath(noteId))

    fun loadAll(): List<Note> = storage
        .listJsonFiles(noteDir.toPath())
        .mapNotNull { path -> storage.load(path, serializer) }
}

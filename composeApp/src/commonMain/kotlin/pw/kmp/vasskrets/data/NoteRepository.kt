package pw.kmp.vasskrets.data

import co.touchlab.kermit.Logger
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import pw.kmp.vasskrets.model.Note
import kotlin.time.ExperimentalTime

@ExperimentalTime
object NoteRepository {
    private val storage: NoteStorage = NoteStorage()
    private val notesDir: Path = "notes".toPath()

    fun save(note: Note): Boolean =
        storage.save(
            path = notesDir / "${note.id}.json",
            content = note,
            serializer = Note.serializer()
        )

    fun get(noteId: String): Note? =
        storage.load(
            path = notesDir / "$noteId.json",
            deserializer = Note.serializer()
        )

    fun delete(noteId: String): Boolean =
        storage.delete(notesDir / "$noteId.json")

    fun loadAll(): List<Note> =
        storage
            .listJsonFiles(notesDir)
            .mapNotNull { path -> storage.load(path, Note.serializer()) }
}

class NoteStorage(
    private val json: Json = Json { prettyPrint = true },
    private val fs: FileSystem = FileSystemDefault,
) {

    fun <T> save(path: Path, content: T, serializer: KSerializer<T>): Boolean {
        return try {
            val text = json.encodeToString(serializer, content)
            fs.write(path, mustCreate = false) { buffer().writeUtf8(text) }
            true
        } catch (e: Exception) {
            Logger.w(e) { "Save failed: $path" }
            false
        }
    }

    fun <T> load(path: Path, deserializer: KSerializer<T>): T? {
        return try {
            if (!fs.exists(path)) return null
            fs.read(path) {
                json.decodeFromString(
                    deserializer,
                    buffer().readUtf8()
                )
            }
        } catch (e: Exception) {
            Logger.w(e) { "Load failed: $path" }
            null
        }
    }

    fun delete(path: Path): Boolean {
        return try {
            fs.delete(path)
            true
        } catch (e: Exception) {
            Logger.w(e) { "Delete failed: $path" }
            false
        }
    }

    fun listJsonFiles(dir: Path): List<Path> {
        return try {
            if (!fs.exists(dir)) return emptyList()
            fs.list(dir).filter { it.name.endsWith(".json") }
        } catch (e: Exception) {
            Logger.w(e) { "List failed: $dir" }
            emptyList()
        }
    }
}

private val FileSystemDefault: FileSystem = FileSystem as FileSystem
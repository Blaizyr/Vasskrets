package pw.kmp.vasskrets.data

import co.touchlab.kermit.Logger
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import pw.kmp.vasskrets.model.Note
import pw.kmp.vasskrets.platform.getPlatformFileSystem
import pw.kmp.vasskrets.platform.provideBaseDir

object NoteRepository {
    private val storage: NoteStorage = NoteStorage()

    private fun noteFilePath(noteId: String): Path =
        provideBaseDir().toPath() / "notes" / "$noteId.json"

    fun save(note: Note): Boolean =
        storage.save(noteFilePath(note.id).name, note, Note.serializer())

    fun saveAndPoint(note: Note): Path? {
        val saveSucceeded = save(note)
        return if (saveSucceeded) noteFilePath(note.id) else null
    }

    fun get(noteId: String): Note? = storage.load(noteFilePath(noteId), Note.serializer())

    fun delete(noteId: String): Boolean = storage.delete(noteFilePath(noteId))

    fun loadAll(): List<Note> =
        storage
            .listJsonFiles(provideBaseDir().toPath() / "notes")
            .mapNotNull { path -> storage.load(path, Note.serializer()) }
}

class NoteStorage(
    private val baseDir: Path = provideBaseDir().toPath(),
    private val json: Json = Json { prettyPrint = true },
    private val fs: FileSystem = getPlatformFileSystem(),
) {
    private fun ensureDirExists(path: Path) {
        val parent = path.parent ?: return
        if (!fs.exists(parent)) fs.createDirectories(parent)
    }

    fun <T> save(filename: String, content: T, serializer: KSerializer<T>): Boolean {
        return try {
            val path = baseDir / filename
            ensureDirExists(path)
            val text = json.encodeToString(serializer, content)
            fs.write(path, mustCreate = false) { buffer().writeUtf8(text) }
            true
        } catch (e: Exception) {
            Logger.w(e) { "Save failed: $filename" }
            false
        }
    }

    fun <T> load(path: Path, deserializer: KSerializer<T>): T? {
        return try {
            if (!fs.exists(path)) return null
            fs.read(path) {
                json.decodeFromString(deserializer, readUtf8())
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

package pw.kmp.vasskrets.data.storage

import co.touchlab.kermit.Logger
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import pw.kmp.vasskrets.platform.getPlatformFileSystem
import pw.kmp.vasskrets.platform.provideBaseDir

class JsonStorage(
    private val baseDir: Path = provideBaseDir().toPath(),
    private val json: Json = Json { prettyPrint = true },
    private val fileSystem: FileSystem = getPlatformFileSystem(),
) {
    private fun ensureDirExists(path: Path) {
        val parent = path.parent ?: return
        if (!fileSystem.exists(parent)) fileSystem.createDirectories(parent)
    }

    fun <T> save(filename: String, content: T, serializer: KSerializer<T>): Boolean {
        return try {
            val path = baseDir / filename
            ensureDirExists(path)
            val text = json.encodeToString(serializer, content)
            fileSystem.write(path, mustCreate = false) { buffer().writeUtf8(text) }
            true
        } catch (e: Exception) {
            Logger.w(e) { "Save failed: $filename" }
            false
        }
    }

    fun <T> saveById(id: String, content: T, serializer: KSerializer<T>, subDir: String = ""): Boolean {
        val fileName = "$id.json"
        val fullPath = if (subDir.isEmpty()) baseDir / fileName else baseDir / subDir / fileName
        return save(fullPath.toString(), content, serializer)
    }

    fun <T> load(path: Path, deserializer: KSerializer<T>): T? {
        return try {
            if (!fileSystem.exists(path)) return null
            fileSystem.read(path) {
                json.decodeFromString(deserializer, readUtf8())
            }
        } catch (e: Exception) {
            Logger.w(e) { "Load failed: $path" }
            null
        }
    }

    fun <T> loadById(id: String, deserializer: KSerializer<T>, subDir: String = ""): T? {
        val fileName = "$id.json"
        val fullPath = if (subDir.isEmpty()) baseDir / fileName else baseDir / subDir / fileName
        return load(fullPath, deserializer)
    }

    fun delete(path: Path): Boolean {
        return try {
            fileSystem.delete(path)
            true
        } catch (e: Exception) {
            Logger.w(e) { "Delete failed: $path" }
            false
        }
    }

    fun listJsonFiles(dir: Path): List<Path> {
        return try {
            if (!fileSystem.exists(dir)) return emptyList()
            fileSystem.list(dir).filter { it.name.endsWith(".json") }
        } catch (e: Exception) {
            Logger.w(e) { "List failed: $dir" }
            emptyList()
        }
    }

}
package pw.kmp.vasskrets.data

import co.touchlab.kermit.Logger
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import pw.kmp.vasskrets.platform.getPlatformFileSystem
import pw.kmp.vasskrets.platform.provideBaseDir

class JsonStorage(
    private val baseDir: Path = provideBaseDir().toPath(),
    private val json: Json = Json { prettyPrint = true },
    private val fileSystem: FileSystem = getPlatformFileSystem(),
) {
/*    fun <T : Metadata> loadMetadatas(type: MetadataType<T>): List<T> {
        val dir = baseDir / type.subDir
        fileSystem.ensureDirsFor(dir)

        return fileSystem.list(dir)
            .filter { it.name.endsWith(".json") }
            .mapNotNull { path ->
                runCatching {
                    val source = fileSystem.read(path) { readUtf8() }
                    val result = Json.decodeFromString(type.metadataDeserializer, source)
                    Logger.d { "Loaded metadata: $result" }
                    result
                }.getOrNull()
            }
    }*/

    fun <T> save(filename: String, content: T, serializer: KSerializer<T>): Boolean {
        return try {
            val path = baseDir / filename
            fileSystem.ensureDirsFor(path)
            val text = json.encodeToString(serializer, content)
            fileSystem.write(path, mustCreate = false) { writeUtf8(text) }
            true
        } catch (e: Exception) {
            Logger.w(e) { "Save failed: $filename" }
            false
        }
    }

    fun <T> saveById(
        id: String,
        content: T,
        serializer: KSerializer<T>,
        subDir: String = "",
    ): Boolean = save(resolvePath(id, subDir).name, content, serializer)

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

    fun <T> loadById(
        id: String,
        deserializer: KSerializer<T>,
        subDir: String = ""
    ): T? = load(resolvePath(id, subDir), deserializer)

    fun delete(path: Path): Boolean {
        return try {
            fileSystem.delete(path)
            true
        } catch (e: Exception) {
            Logger.w(e) { "Delete failed: $path" }
            false
        }
    }

    fun delete(id: String): Boolean = delete(resolvePath(id))

    fun listJsonFiles(dir: Path): List<Path> {
        return try {
            if (!fileSystem.exists(dir)) return emptyList()
            fileSystem.list(dir).filter { it.name.endsWith(".json") }
        } catch (e: Exception) {
            Logger.w(e) { "List failed: $dir" }
            emptyList()
        }
    }

    private fun resolvePath(id: String, subDir: String = ""): Path {
        val fileName = "$id.json"
        return if (subDir.isEmpty()) baseDir / fileName else baseDir / subDir / fileName
    }

}

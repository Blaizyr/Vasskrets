package pw.kmp.vasskrets

import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.fakefilesystem.FakeFileSystem
import okio.use
import pw.kmp.vasskrets.data.NoteStorage
import pw.kmp.vasskrets.model.Note
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime

@ExperimentalTime
class NoteStorageTest {

    private lateinit var fs: FileSystem
    private lateinit var storage: NoteStorage
    private lateinit var baseDirPath: Path

    @BeforeTest
    fun setup() {
        fs = FakeFileSystem()
        baseDirPath = "/unit-tests/notes".toPath()

        fun createDirsRecursively(path: Path) {
            val parent = path.parent
            if (parent != null && !fs.exists(parent)) {
                createDirsRecursively(parent)
            }
            if (!fs.exists(path)) {
                fs.createDirectory(path)
                println("Created directory: $path")
            }
        }

        if (!fs.exists(baseDirPath)) {
            createDirsRecursively(baseDirPath)
        }

        val exampleFile = baseDirPath / "testfile.txt"
        val parentDir = exampleFile.parent ?: error("File musi mieć katalog nadrzędny!")

        if (!fs.exists(parentDir)) {
            createDirsRecursively(parentDir)
        }

        fs.sink(exampleFile).buffer().use { sink ->
            sink.writeUtf8("Testowy zapis w FakeFileSystem")
        }

        println("Zawartość katalogu po zapisie pliku:")
        fs.list(baseDirPath).forEach { println(it) }

        storage = NoteStorage(
            baseDir = baseDirPath,
            fs = fs
        )
    }

    @Test
    fun `save should write a JSON file`() {
        val note = Note(title = "Test", content = "Zawartość")
        val success = storage.save("note1.json", note, Note.serializer())

        assertTrue(success)

        val files = fs.list(baseDirPath)
        assertEquals(2, files.size) // testfile.txt + note1.json
        assertTrue(files.any { it.name.endsWith(".json") })
    }

    @Test
    fun `load should read and deserialize JSON file`() {
        val note = Note(title = "Title", content = "Body")
        val path = baseDirPath / "test.json"
        val jsonText = Json.encodeToString(Note.serializer(), note)
        fs.write(path) { writeUtf8(jsonText) }

        val loaded = storage.load(path, Note.serializer())
        assertNotNull(loaded)
        assertEquals(note.title, loaded.title)
        assertEquals(note.content, loaded.content)
    }

    @Test
    fun `delete should remove file`() {
        val path = baseDirPath / "to-delete.json"
        fs.write(path) { writeUtf8("{}") }

        val result = storage.delete(path)
        assertTrue(result)
        assertFalse(fs.exists(path))
    }

    @Test
    fun `listJsonFiles returns only json files`() {
        fs.write(baseDirPath / "a.json") { writeUtf8("{}") }
        fs.write(baseDirPath / "b.txt") { writeUtf8("nie json") }

        val list = storage.listJsonFiles(baseDirPath)
        assertEquals(1, list.size)
        assertTrue(list.first().name.endsWith(".json"))
    }
}

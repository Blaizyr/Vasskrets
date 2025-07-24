package pw.kmp.vasskrets

import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import pw.kmp.vasskrets.data.JsonStorage
import pw.kmp.vasskrets.domain.note.Note
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime

@ExperimentalTime
class JsonStorageTest {

    private lateinit var fs: FileSystem
    private lateinit var storage: JsonStorage
    private lateinit var baseDirPath: Path

    @BeforeTest
    fun setup() {
        baseDirPath = "/unit-tests/notes".toPath()
        fs = FakeFileSystem().apply {
            ensureDirsFor(baseDirPath)
        }
        storage = JsonStorage(baseDirPath, Json, fs)
    }

    @Test
    fun `save should write a JSON file`() {
        val path = baseDirPath / "note1.json"
        val content = "Zawartość"
        val jsonFile = storage.createTestFile(path.name, content, baseDirPath)
        val result = fs.read(jsonFile) { readUtf8() }
        assertTrue(result.contains("Zawartość"))
        assertTrue(jsonFile.name.endsWith(".json"))
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

    @Test
    fun `parent directories exist after ensuring`() {
        val testLeaf = "/a/b/c/d/exception.jpg".toPath()

        fs.ensureDirsFor(testLeaf)

        val expectedDirs = listOf(
            "/a",
            "/a/b",
            "/a/b/c",
            "/a/b/c/d",
        ).map { it.toPath() }

        expectedDirs.forEach { it.shouldExist(fs) }
    }
}
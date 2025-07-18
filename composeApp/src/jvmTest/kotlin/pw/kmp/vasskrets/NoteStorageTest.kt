package pw.kmp.vasskrets

import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
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

    @BeforeTest
    fun setup() {
        fs = FakeFileSystem()
    }

    @Test
    fun `save should write a JSON file`() {
        val note = Note(title = "Test", content = "Zawartość")
        val success = storage.save("note1.json", note, Note.serializer())

        assertTrue(success)
        val files = fs.list("/notes".toPath())
        assertEquals(1, files.size)
        assertTrue(files.first().name.endsWith(".json"))
    }

    @Test
    fun `load should read and deserialize JSON file`() {
        val note = Note(title = "Title", content = "Body")
        val path = "/notes/test.json".toPath()
        val jsonText = Json.encodeToString(Note.serializer(), note)
        fs.write(path) {
            writeUtf8(jsonText)
        }

        val loaded = storage.load(path, Note.serializer())
        assertNotNull(loaded)
        assertEquals(note.title, loaded.title)
        assertEquals(note.content, loaded.content)
    }

    @Test
    fun `delete should remove file`() {
        val path = "/notes/to-delete.json".toPath()
        fs.write(path) { writeUtf8("{}") }

        val result = storage.delete(path)
        assertTrue(result)
        assertFalse(fs.exists(path))
    }

    @Test
    fun `listJsonFiles returns only json files`() {
        fs.write("/notes/a.json".toPath()) { writeUtf8("{}") }
        fs.write("/notes/b.txt".toPath()) { writeUtf8("nie json") }

        val list = storage.listJsonFiles("/notes".toPath())
        assertEquals(1, list.size)
        assertTrue(list.first().name.endsWith(".json"))
    }
}

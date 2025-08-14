package pw.kmp.vasskrets

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import pw.kmp.vasskrets.data.JsonStorage
import pw.kmp.vasskrets.domain.note.Note
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal fun Path.shouldExist(fs: FileSystem) {
    check(fs.exists(this)) { "Expected $this to exist" }
}

@OptIn(ExperimentalUuidApi::class)
internal fun JsonStorage.createTestFile(
    title: String,
    content: String,
    path: Path = "/notes".toPath(),
): Path {
    val note = Note(Uuid.random(), title, content)
    this.save("$title.json", note, Note.serializer())
    return "$path/$title.json".toPath()
}

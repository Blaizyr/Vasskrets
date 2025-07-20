package pw.kmp.vasskrets

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import pw.kmp.vasskrets.data.NoteStorage
import pw.kmp.vasskrets.model.Note

internal val Path.isFile: Boolean get() = this.name.contains('.')

internal fun Path.shouldExist(fs: FileSystem) {
    check(fs.exists(this)) { "Expected $this to exist" }
}

internal fun NoteStorage.createTestFile(title: String, content: String, path: Path = "/notes".toPath()): Path {
    val note = Note("id", title, content)
    this.save("$title.json", note, Note.serializer())
    return "$path/$title.json".toPath()
}

internal fun FileSystem.ensureDirsFor(path: Path) {
    val dirs = generateSequence(path) { it.parent }
        .toList()
        .reversed()
        .let {
            if (it.last().isFile) it.dropLast(1)
            else it
        }

    dirs.forEach { dir ->
        if (!exists(dir)) createDirectory(dir)
    }
}

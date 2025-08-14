package pw.kmp.vasskrets.data

import okio.FileSystem
import okio.Path

internal val Path.isFile: Boolean get() = this.name.contains('.')

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

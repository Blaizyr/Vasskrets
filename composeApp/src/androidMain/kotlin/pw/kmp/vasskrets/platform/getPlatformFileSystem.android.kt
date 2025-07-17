package pw.kmp.vasskrets.platform

import okio.FileSystem

actual fun getPlatformFileSystem(): FileSystem {
    return FileSystem.SYSTEM
}

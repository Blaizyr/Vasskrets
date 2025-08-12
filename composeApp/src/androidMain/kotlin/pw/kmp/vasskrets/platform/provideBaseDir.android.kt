package pw.kmp.vasskrets.platform

import pw.kmp.vasskrets.platform.AndroidAppContextHolder.appContext

actual fun provideBaseDir(): String {
    return  appContext.filesDir.absolutePath
}

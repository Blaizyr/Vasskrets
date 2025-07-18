package pw.kmp.vasskrets.platform

actual fun provideBaseDir(): String {
    val userHome = System.getProperty("user.home") ?: "."
    return "$userHome/.vasskrets/notes"
}

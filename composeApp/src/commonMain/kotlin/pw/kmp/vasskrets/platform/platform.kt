package pw.kmp.vasskrets.platform

interface Platform {
    val name: String
}

expect val platform: Platform
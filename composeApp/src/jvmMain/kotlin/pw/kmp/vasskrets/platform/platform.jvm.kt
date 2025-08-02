package pw.kmp.vasskrets.platform

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual val platform: Platform
    get() = JVMPlatform()
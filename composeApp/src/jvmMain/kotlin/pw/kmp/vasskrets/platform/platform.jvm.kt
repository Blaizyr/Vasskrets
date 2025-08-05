package pw.kmp.vasskrets.platform

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
    override val family: PlatformFamily = PlatformFamily.JVM
    override val type: PlatformType = PlatformType.JVM
}

actual val platform: Platform = JVMPlatform()

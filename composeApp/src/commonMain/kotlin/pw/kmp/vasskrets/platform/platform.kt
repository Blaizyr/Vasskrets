package pw.kmp.vasskrets.platform

interface Platform {
    val name: String
    val family: PlatformFamily
    val type: PlatformType
}

expect val platform: Platform

enum class PlatformFamily {
    Android, Apple, JVM, Web
}

enum class PlatformType {
    AndroidPhone,
    AndroidTablet,
    AndroidTV,
    AndroidWear,
    AndroidAuto,

    IOS,
    MacOS,
    JVM,
    DesktopLinux,
    DesktopWindows,
    WasmDesktop,
    WasmMobile,

    UnitTest,
    SnapshotTest,

    AppleVisionOS,
    MetaQuest,

    Unknown
}

package pw.kmp.vasskrets.platform


class WasmPlatform : Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual val platform: Platform
    get() = WasmPlatform()

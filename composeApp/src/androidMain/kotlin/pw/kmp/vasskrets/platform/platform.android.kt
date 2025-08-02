package pw.kmp.vasskrets.platform

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual val platform: Platform
    get() = AndroidPlatform()
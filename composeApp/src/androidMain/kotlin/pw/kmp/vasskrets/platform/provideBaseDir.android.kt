package pw.kmp.vasskrets.platform

import android.content.Context

private lateinit var appContext: Context

fun initPlatformContext(context: Context) {
    appContext = context.applicationContext
}

actual fun provideBaseDir(): String {
    return  appContext.filesDir.absolutePath
}

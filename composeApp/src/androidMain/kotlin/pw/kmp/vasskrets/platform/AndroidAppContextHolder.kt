package pw.kmp.vasskrets.platform

import android.content.Context

object AndroidAppContextHolder {
    lateinit var appContext: Context
        private set

    fun init(context: Context) {
        appContext = context.applicationContext
    }
}

fun initPlatformContext(context: Context) {
    AndroidAppContextHolder.init(context)
}

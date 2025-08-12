package pw.kmp.vasskrets.di

import android.content.Context
import org.koin.dsl.module
import pw.kmp.vasskrets.platform.AndroidAppContextHolder

val AndroidModule = module {
    single<Context> { AndroidAppContextHolder.appContext }
}

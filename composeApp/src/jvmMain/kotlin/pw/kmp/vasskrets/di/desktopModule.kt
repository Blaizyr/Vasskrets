package pw.kmp.vasskrets.di

import org.koin.dsl.module
import pw.kmp.vasskrets.DefaultWindowManager
import pw.kmp.vasskrets.WindowManager

val desktopModule = module {
    single<WindowManager> { DefaultWindowManager() }
}

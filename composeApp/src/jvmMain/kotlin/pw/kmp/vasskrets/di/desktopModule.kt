package pw.kmp.vasskrets.di

import org.koin.dsl.module
import pw.kmp.vasskrets.ui.windowing.DefaultWindowManager
import pw.kmp.vasskrets.ui.windowing.WindowManager

val desktopModule = module {
    single<WindowManager> { DefaultWindowManager() }
}

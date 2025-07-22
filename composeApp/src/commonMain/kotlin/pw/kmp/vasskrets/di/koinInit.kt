package pw.kmp.vasskrets.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    appDeclaration: KoinAppDeclaration = {},
    platformModule: Module? = null
) = startKoin {
    appDeclaration()
    modules(listOfNotNull(sharedModule , platformModule))
}

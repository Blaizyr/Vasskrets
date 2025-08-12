package pw.kmp.vasskrets.navigation.domain

sealed class NavEvent<T> {
    data class Open<T>(val config: T) : NavEvent<T>()
    data class Close<T>(val config: T) : NavEvent<T>()
}
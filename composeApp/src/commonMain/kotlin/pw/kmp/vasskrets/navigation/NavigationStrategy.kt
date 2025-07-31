package pw.kmp.vasskrets.navigation

interface NavigationStrategy {
    fun dispatch(intent: ViewIntent): NavigationAction
}

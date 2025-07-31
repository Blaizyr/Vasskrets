package pw.kmp.vasskrets.navigation

sealed interface NavigationAction {
    data class CreateAndShow(val target: ViewTarget) : NavigationAction
    data class FocusExisting(val target: ViewTarget) : NavigationAction
    data class OpenNewTab(val target: ViewTarget) : NavigationAction
    data class ReplaceView(val target: ViewTarget) : NavigationAction
    data object Noop : NavigationAction
}

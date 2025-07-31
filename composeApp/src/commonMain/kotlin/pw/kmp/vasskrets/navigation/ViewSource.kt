package pw.kmp.vasskrets.navigation

sealed interface ViewSource {
    data object NavigationTargetClick : ViewSource
    data object TabClick : ViewSource
    data object DeepLink : ViewSource
    data object LocalEvent : ViewSource
    data object StateRestore : ViewSource
}

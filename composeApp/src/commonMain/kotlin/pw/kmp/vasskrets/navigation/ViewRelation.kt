package pw.kmp.vasskrets.navigation

sealed interface ViewRelation {
    data object ShowAsDetail : ViewRelation
    data object OpenAsTab : ViewRelation
    data object FocusView : ViewRelation
    data object ReplaceView : ViewRelation
}

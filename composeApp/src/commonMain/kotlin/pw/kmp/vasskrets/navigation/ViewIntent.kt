package pw.kmp.vasskrets.navigation

data class ViewIntent(
    val source: ViewSource,
    val target: ViewTarget,
    val relation: ViewRelation
)

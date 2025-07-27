package pw.kmp.vasskrets.ui.adaptive

import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

enum class SizeClass { Compact, Medium, Expanded }

fun IntSize.classifySize(): SizeClass {
    val width = this.width.dp
    return when {
        width < 600.dp -> SizeClass.Compact
        width < 840.dp -> SizeClass.Medium
        else -> SizeClass.Expanded
    }
}

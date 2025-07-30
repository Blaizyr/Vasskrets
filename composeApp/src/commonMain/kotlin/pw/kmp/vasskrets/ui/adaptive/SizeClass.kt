package pw.kmp.vasskrets.ui.adaptive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalWindowInfo


enum class SizeClass { Compact, Medium, Expanded }

@Composable
fun rememberWindowSizeClass(): SizeClass {
    val windowSize = LocalWindowInfo.current.containerSize
    return remember(windowSize) {
        when {
            windowSize.width < 600 -> SizeClass.Compact
            windowSize.width < 840 -> SizeClass.Medium
            else -> SizeClass.Expanded
        }
    }
}

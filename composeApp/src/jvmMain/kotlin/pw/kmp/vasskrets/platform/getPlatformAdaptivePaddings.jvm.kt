package pw.kmp.vasskrets.platform

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

@Composable
actual fun getPlatformAdaptivePaddings(): PaddingValues {
    val windowInfo = LocalWindowInfo.current
    val widthPx = windowInfo.containerSize.width

    return when {
        widthPx < 600 -> PaddingValues(horizontal = 12.dp)
        widthPx < 1200 -> PaddingValues(horizontal = 24.dp)
        else -> PaddingValues(horizontal = 64.dp)
    }
}

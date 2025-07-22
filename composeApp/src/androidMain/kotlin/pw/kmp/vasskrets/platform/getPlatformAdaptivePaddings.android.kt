package pw.kmp.vasskrets.platform

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

@Composable
actual fun getPlatformAdaptivePaddings(): PaddingValues {
    val windowInfo = LocalWindowInfo.current
    val widthDp = windowInfo.containerSize.width

    val horizontalPadding = when {
        widthDp < 400 -> 8.dp
        widthDp < 800 -> 16.dp
        else -> 32.dp
    }
    return PaddingValues(horizontal = horizontalPadding, vertical = 16.dp)
}

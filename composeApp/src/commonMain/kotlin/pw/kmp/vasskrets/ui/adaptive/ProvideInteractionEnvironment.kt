package pw.kmp.vasskrets.ui.adaptive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import pw.kmp.vasskrets.InteractionEnvironment

@Composable
fun ProvideInteractionEnvironment(env: InteractionEnvironment) {
    val sizeClass = rememberWindowSizeClass()

    LaunchedEffect( sizeClass) {
        env.updateScreenSizeClass(sizeClass)
    }
}

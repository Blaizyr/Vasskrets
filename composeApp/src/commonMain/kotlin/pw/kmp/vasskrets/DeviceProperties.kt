package pw.kmp.vasskrets

import androidx.compose.ui.input.InputMode
import pw.kmp.vasskrets.platform.Platform

interface DeviceProperties {
    val platform: Platform
    val inputMode: InputMode
//    val runtimeMode: RuntimeMode
//    val featureFlags: Set<FeatureFlag>
}

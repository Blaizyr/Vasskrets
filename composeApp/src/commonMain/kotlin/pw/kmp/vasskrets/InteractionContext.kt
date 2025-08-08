package pw.kmp.vasskrets

import pw.kmp.vasskrets.platform.Platform
import pw.kmp.vasskrets.ui.adaptive.SizeClass

interface InteractionContext {
    val currentPlatform: Platform
//    val deviceType: DeviceType
    var screenSizeClass: SizeClass
//    val userPreferences: UserPreferences
//    val runtimeMode: RuntimeMode
}

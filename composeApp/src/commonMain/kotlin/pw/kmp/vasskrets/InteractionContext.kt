package pw.kmp.vasskrets

import pw.kmp.vasskrets.platform.Platform
import pw.kmp.vasskrets.platform.platform
import pw.kmp.vasskrets.ui.adaptive.SizeClass

interface InteractionContext {
    val currentPlatform: Platform
//    val deviceType: DeviceType
    var screenSizeClass: SizeClass
//    val userPreferences: UserPreferences
//    val runtimeMode: RuntimeMode
}

class InteractionEnvironment : InteractionContext {
    override val currentPlatform: Platform = platform
    override var screenSizeClass: SizeClass = SizeClass.Compact

    fun updateScreenSizeClass(sizeClass: SizeClass) {
        println("${this.screenSizeClass} is changed to $sizeClass")
        this.screenSizeClass = sizeClass
    }
}

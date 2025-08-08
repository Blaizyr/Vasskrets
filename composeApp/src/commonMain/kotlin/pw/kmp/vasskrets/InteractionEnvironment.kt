package pw.kmp.vasskrets

import pw.kmp.vasskrets.platform.Platform
import pw.kmp.vasskrets.platform.platform
import pw.kmp.vasskrets.ui.adaptive.SizeClass

class InteractionEnvironment : InteractionContext {
    override val currentPlatform: Platform = platform
    override var screenSizeClass: SizeClass = SizeClass.Compact

    fun updateScreenSizeClass(sizeClass: SizeClass) {
        this.screenSizeClass = sizeClass
    }
}

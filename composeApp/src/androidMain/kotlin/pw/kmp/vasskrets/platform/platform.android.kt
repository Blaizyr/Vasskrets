package pw.kmp.vasskrets.platform

import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration.UI_MODE_TYPE_CAR
import android.content.res.Configuration.UI_MODE_TYPE_TELEVISION
import android.content.res.Configuration.UI_MODE_TYPE_WATCH
import android.os.Build
import androidx.core.content.getSystemService
import org.koin.mp.KoinPlatform.getKoin
import pw.kmp.vasskrets.platform.PlatformType.AndroidAuto
import pw.kmp.vasskrets.platform.PlatformType.AndroidPhone
import pw.kmp.vasskrets.platform.PlatformType.AndroidTV
import pw.kmp.vasskrets.platform.PlatformType.AndroidTablet
import pw.kmp.vasskrets.platform.PlatformType.AndroidWear

class AndroidPlatform(override val type: PlatformType) : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val family: PlatformFamily = PlatformFamily.Android
}

actual val platform: Platform
    get() {
        val context = getKoin().get<Context>()

        val platformType = when (context.getSystemService<UiModeManager>()?.currentModeType) {
            UI_MODE_TYPE_TELEVISION -> AndroidTV
            UI_MODE_TYPE_WATCH -> AndroidWear
            UI_MODE_TYPE_CAR -> AndroidAuto
            else -> {
                val metrics = context.resources.displayMetrics
                val widthDp = metrics.widthPixels / metrics.density
                val heightDp = metrics.heightPixels / metrics.density
                val smallestWidth = minOf(widthDp, heightDp)
                if (smallestWidth >= 600) AndroidTablet else AndroidPhone
            }
        }

        return AndroidPlatform(type = platformType)
    }

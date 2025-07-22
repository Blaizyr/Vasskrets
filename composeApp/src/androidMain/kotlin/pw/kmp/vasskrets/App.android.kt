package pw.kmp.vasskrets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import pw.kmp.vasskrets.di.initKoin
import pw.kmp.vasskrets.platform.initPlatformContext
import pw.kmp.vasskrets.ui.App
import kotlin.time.ExperimentalTime

@ExperimentalTime
class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initPlatformContext(this)
        initKoin()
        setContent { App() }
    }
}

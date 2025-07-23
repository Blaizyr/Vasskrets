import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import kotlinx.browser.document
import pw.kmp.vasskrets.components.root.RootComponent
import pw.kmp.vasskrets.di.initKoin
import pw.kmp.vasskrets.ui.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()
    val body = document.body ?: return
    ComposeViewport(body) {
        val rootComponent = RootComponent(
            componentContext = DefaultComponentContext(lifecycle = LifecycleRegistry()),
        )
        App(rootComponent)
    }

}

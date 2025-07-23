import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import pw.kmp.vasskrets.components.root.RootComponent
import pw.kmp.vasskrets.di.initKoin
import pw.kmp.vasskrets.ui.App
import java.awt.Dimension

fun main() = application {
    initKoin()
    Window(
        title = "Vasskrets",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        val rootComponent = RootComponent(
            componentContext = DefaultComponentContext(lifecycle = LifecycleRegistry()),
        )
        window.minimumSize = Dimension(350, 600)
        App(rootComponent)
    }

}

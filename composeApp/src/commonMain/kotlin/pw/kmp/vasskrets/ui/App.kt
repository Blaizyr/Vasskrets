package pw.kmp.vasskrets.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.jetbrains.compose.ui.tooling.preview.Preview
import pw.kmp.vasskrets.components.root.RootComponent
import pw.kmp.vasskrets.ui.login.LoginScreen

@Preview
@Composable
internal fun App(root: RootComponent) {
    val session by root.currentSession.collectAsState()

    when (/*session*/"logged in ;)") {
        null -> LoginScreen(root.loginComponent)
        else -> NavigationLayout(root.mainNavigator)
    }
}

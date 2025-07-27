import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import pw.kmp.vasskrets.ui.ScreenTarget
import pw.kmp.vasskrets.ui.adaptive.SizeClass
import pw.kmp.vasskrets.ui.adaptive.classifySize

@Composable
fun AdaptiveNavigationScaffold(
    currentTarget: ScreenTarget,
    onNavigate: (ScreenTarget) -> Unit,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable (() -> Unit)? = null,
    drawerContent: @Composable (() -> Unit)? = null,
    railContent: @Composable (() -> Unit)? = null,
    content: @Composable (ScreenTarget) -> Unit,
) {
    val windowInfo = LocalWindowInfo.current
    val sizeClass = remember(windowInfo) {
        windowInfo.containerSize.classifySize()
    }

    when (sizeClass) {
        SizeClass.Compact -> {
            Scaffold(
                topBar = topBar,
                bottomBar = bottomBar ?: { BottomAppBar(/*currentTarget, onNavigate*/) {} },
                content = { content(currentTarget) }
            )
        }

        SizeClass.Medium -> {
            ModalNavigationDrawer(
                drawerContent = drawerContent ?: {
                    ModalDrawerSheet(
                        content = {
                            Column {
                                Text("Drawer")
                                Button(onClick = { }) {
                                    Text("Go")
                                }
                            }
                        },
                        drawerState = rememberDrawerState(DrawerValue.Closed),
                    )
                },
                content = {
                    Scaffold(
                        topBar = topBar,
                        content = { content(currentTarget) }
                    )
                }
            )
        }

        SizeClass.Expanded -> {
            Row {
                railContent?.invoke()
                    ?: NavigationRail(modifier = Modifier /*currentTarget, onNavigate*/) {
                        Column {
                            Text(
                                "Rail"
                            )
                        }
                    }
                Scaffold(
                    topBar = topBar,
                    content = { content(currentTarget) }
                )
            }
        }
    }
}


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import pw.kmp.vasskrets.components.NavigationTarget
import pw.kmp.vasskrets.ui.adaptive.SizeClass
import pw.kmp.vasskrets.ui.adaptive.rememberWindowSizeClass

@Composable
fun AdaptiveNavigationScaffold(
    navigationTargets: List<NavigationTarget>,
    topBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val windowInfo = LocalWindowInfo.current
    val sizeClass = rememberWindowSizeClass()
    when (sizeClass) {
        SizeClass.Compact -> {
            Scaffold(
                topBar = topBar,
                bottomBar = {
                    NavigationBar(containerColor = Color(0xFF222222)) {
                        navigationTargets.forEach { target ->
                            NavigationBarItem(
                                selected = target.isSelected,
                                onClick = target.onClick,
                                icon = target.icon,
                                label = {
                                    Text(
                                        text = target.label,
                                        maxLines = 1
                                    )
                                }
                            )
                        }
                    }
                },
                content = { content() }
            )
        }

        SizeClass.Medium -> {
            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet {
                        Column(modifier = Modifier.padding(16.dp)) {
                            navigationTargets.forEach { target ->
                                NavigationDrawerItem(
                                    label = { Text(target.label) },
                                    selected = target.isSelected,
                                    onClick = target.onClick,
                                    icon = target.icon
                                )
                            }
                        }
                    }
                },
                content = {
                    Scaffold(
                        topBar = topBar,
                        content = { content() }
                    )
                }
            )
        }

        SizeClass.Expanded -> {
            Row {
                NavigationRail(modifier = Modifier, containerColor = Color(0xFF222222)) {
                    navigationTargets.forEach { target ->
                        NavigationRailItem(
                            selected = target.isSelected,
                            onClick = target.onClick,
                            icon = target.icon,
                            label = { Text(target.label, maxLines = 1) }
                        )
                    }
                }
                Scaffold(
                    topBar = topBar,
                    content = { content() }
                )
            }
        }
    }
}

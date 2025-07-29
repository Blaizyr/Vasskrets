package pw.kmp.vasskrets.ui

import AdaptiveNavigationScaffold
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import pw.kmp.vasskrets.components.NavigationComponent
import pw.kmp.vasskrets.components.NavigationConfig
import pw.kmp.vasskrets.components.root.Child.Conversations
import pw.kmp.vasskrets.components.root.Child.Home
import pw.kmp.vasskrets.components.root.Child.Notes
import pw.kmp.vasskrets.components.root.Child.Profile
import pw.kmp.vasskrets.components.root.Child.Settings
import pw.kmp.vasskrets.ui.conversation.ConversationNavigation
import pw.kmp.vasskrets.ui.theme.AppTheme

@Composable
fun NavigationLayout(
    component: NavigationComponent,
) {
    val childStack by component.navigationStack.subscribeAsState()

    AdaptiveNavigationScaffold(
        currentTarget = resolveTarget(childStack.active.instance),
        onNavigate = { navigationTarget ->
            component.navigateTo(navigationTarget.toNavigationConfig())
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF222222)
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { component.navigateTo(NavigationConfig.Home) },
                    icon = {},
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { component.navigateTo(NavigationConfig.Notes) },
                    icon = {},
                    label = { Text("Notes") }
                )
            }
        },
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.background(Color(0xFFBBDEFB))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    Text("Drawer", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { component.navigateTo(NavigationConfig.Home) }) {
                        Text("Go Home")
                    }
                }
            }
        },
        railContent = {
            NavigationRail(
                containerColor = Color(0xFFB2EBF2)
            ) {
                NavigationRailItem(
                    selected = false,
                    onClick = { component.navigateTo(NavigationConfig.Home) },
                    icon = {
                        Box(
                            Modifier
                                .size(24.dp)
                                .background(Color.Gray, CircleShape)
                        )
                    },
                    label = {
                        Text("Home", color = Color.Black)
                    }
                )
            }
        }
    ) { _ ->
        Children(stack = childStack) {
            AppTheme {
                when (val child = it.instance) {
                    is Home -> HomeScreen(child.component)
                    is Notes -> NotesScreen(child.component)
                    is Conversations -> { ConversationNavigation(child.component)        }

                    is Profile -> TODO()
                    is Settings -> TODO()
                }
            }
        }
    }
}
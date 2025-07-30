package pw.kmp.vasskrets.ui

import AdaptiveNavigationScaffold
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import pw.kmp.vasskrets.components.NavigationComponent
import pw.kmp.vasskrets.components.NavigationConfig
import pw.kmp.vasskrets.components.NavigationTarget
import pw.kmp.vasskrets.components.root.Child.Conversations
import pw.kmp.vasskrets.components.root.Child.Home
import pw.kmp.vasskrets.components.root.Child.Notes
import pw.kmp.vasskrets.components.root.Child.Profile
import pw.kmp.vasskrets.components.root.Child.Settings
import pw.kmp.vasskrets.ui.conversation.ConversationNavigation
import pw.kmp.vasskrets.ui.theme.AppTheme

@Composable
fun NavigationLayout(component: NavigationComponent) {
    val childStack by component.navigationStack.subscribeAsState()
    val currentTarget = resolveTarget(childStack.active.instance)

    val targets = listOf(
        NavigationTarget(
            id = NavigationConfig.Home.toString(),
            label = "Home",
            icon = {
                IconPlacehoder()
            },
            isSelected = currentTarget == ScreenTarget.Home,
            onClick = { component.navigateTo(NavigationConfig.Home) }
        ),
        NavigationTarget(
            id = NavigationConfig.Notes.toString(),
            label = "Notes",
            icon = {
                IconPlacehoder()
            },
            isSelected = currentTarget == ScreenTarget.Notes,
            onClick = { component.navigateTo(NavigationConfig.Notes) }
        ),
        NavigationTarget(
            id = NavigationConfig.Conversations.toString(),
            label = "Conversations",
            icon = {
                IconPlacehoder()
            },
            isSelected = currentTarget == ScreenTarget.Conversations,
            onClick = { component.navigateTo(NavigationConfig.Conversations) }
        ),
    )

    AdaptiveNavigationScaffold(
        navigationTargets = targets,
        topBar = {},
        content = {
            AppTheme {
                Children(stack = childStack) {
                    when (val child = it.instance) {
                        is Home -> HomeScreen(child.component)
                        is Notes -> NotesScreen(child.component)
                        is Conversations -> ConversationNavigation(child.component)

                        is Profile -> TODO()
                        is Settings -> TODO()
                    }
                }
            }
        }
    )
}

@Composable
fun IconPlacehoder() {
    Box(
        Modifier
            .size(24.dp)
            .background(Color.Gray, CircleShape)
    )
}
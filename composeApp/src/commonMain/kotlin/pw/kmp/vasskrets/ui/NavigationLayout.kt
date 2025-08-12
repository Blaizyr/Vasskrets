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
import pw.kmp.vasskrets.components.root.Child.Conversations
import pw.kmp.vasskrets.components.root.Child.Home
import pw.kmp.vasskrets.components.root.Child.Notes
import pw.kmp.vasskrets.components.root.Child.Profile
import pw.kmp.vasskrets.components.root.Child.Settings
import pw.kmp.vasskrets.navigation.NavItem
import pw.kmp.vasskrets.navigation.main.MainNavigationConfig
import pw.kmp.vasskrets.navigation.main.NavigationComponent
import pw.kmp.vasskrets.ui.conversation.ConversationNavigation
import pw.kmp.vasskrets.ui.home.HomeScreen
import pw.kmp.vasskrets.ui.note.NotesScreen

@Composable
fun NavigationLayout(component: NavigationComponent) {
    val childStack by component.navigationStack.subscribeAsState()
    val currentTarget = resolveTarget(childStack.active.instance)

    val targets = listOf(
        NavItem(
            id = MainNavigationConfig.Home.toString(),
            label = "Home",
            icon = {
                IconPlaceholder()
            },
            isSelected = currentTarget == ScreenTarget.Home,
            onClick = { component.navigateTo(MainNavigationConfig.Home) }
        ),
        NavItem(
            id = MainNavigationConfig.Notes.toString(),
            label = "Notes",
            icon = {
                IconPlaceholder()
            },
            isSelected = currentTarget == ScreenTarget.Notes,
            onClick = { component.navigateTo(MainNavigationConfig.Notes) }
        ),
        NavItem(
            id = MainNavigationConfig.Conversations.toString(),
            label = "Conversations",
            icon = {
                IconPlaceholder()
            },
            isSelected = currentTarget == ScreenTarget.Conversations,
            onClick = { component.navigateTo(MainNavigationConfig.Conversations) }
        ),
    )

    AdaptiveNavigationScaffold(
        navItems = targets,
        topBar = {},
        content = {
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
    )
}

@Composable
fun IconPlaceholder() {
    Box(
        Modifier
            .size(24.dp)
            .background(Color.Gray, CircleShape)
    )
}
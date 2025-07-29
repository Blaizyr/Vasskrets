package pw.kmp.vasskrets.ui.conversation

import AdaptiveNavigationScaffold
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.push
import pw.kmp.vasskrets.components.conversation.ConversationNodeComponent
import pw.kmp.vasskrets.ui.ScreenTarget
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ConversationNavigation(node: ConversationNodeComponent) {
    val childStack by node.childStack.subscribeAsState()
    val navi by node.routerV2.activeConfigs.collectAsState()
    AdaptiveNavigationScaffold(
        currentTarget = ScreenTarget.Conversations,
        onNavigate = { _ -> },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF222222)
            ) {
                navi.forEach {
                    NavigationBarItem(
                        selected = false,
                        onClick = { node.navigation.push(it) },
                        icon = {},
                        label = { Text("${it.id}") }
                    )
                }
            }
        }
    ) { _ ->
        Children(stack = childStack) {
            ConversationScreen(it.instance.component)
        }
    }
}

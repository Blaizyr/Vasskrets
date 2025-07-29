package pw.kmp.vasskrets.ui.conversation

import AdaptiveNavigationScaffold
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    AdaptiveNavigationScaffold(
        currentTarget = ScreenTarget.Conversations,
        onNavigate = { _ -> },
        bottomBar = {
            BottomAppBar {
                Row {
                    node.routerV2.activeConfigs.value.forEach {
                        TextButton(onClick = { node.navigation.push(it) }) {
                            Text(text = it.id.toString())
                        }
                    }
                }
            }
        }
    ) { _ ->
        Children(stack = childStack) {
            ConversationScreen(it.instance)
        }
    }
}

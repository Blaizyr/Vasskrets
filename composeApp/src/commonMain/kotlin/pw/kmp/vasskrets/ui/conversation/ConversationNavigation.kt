@file:OptIn(ExperimentalUuidApi::class)

package pw.kmp.vasskrets.ui.conversation

import AdaptiveNavigationScaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.push
import pw.kmp.vasskrets.components.NavigationTarget
import pw.kmp.vasskrets.components.conversation.ConversationNodeComponent
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun ConversationNavigation(node: ConversationNodeComponent) {
    val childStack by node.childStack.subscribeAsState()
    val activeConfigs by node.routerV2.activeConfigs.collectAsState()

    AdaptiveNavigationScaffold(
        navigationTargets =
            activeConfigs.map { config ->
                NavigationTarget(
                    id = config.id.toString(),
                    isSelected = false,
                    onClick = { node.navigation.push(config) },
                    icon = { Text("💬") },
                    label = config.metadata.title ?: ""
                )
            },
        content = {
            Children(stack = childStack) {
                ConversationScreen(it.instance.component)
            }
        }
    )
}

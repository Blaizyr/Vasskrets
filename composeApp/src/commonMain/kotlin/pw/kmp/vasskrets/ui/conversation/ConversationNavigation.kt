@file:OptIn(ExperimentalUuidApi::class)

package pw.kmp.vasskrets.ui.conversation

import AdaptiveNavigationScaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import pw.kmp.vasskrets.components.conversation.ConversationNodeComponent
import pw.kmp.vasskrets.navigation.NavigationTarget
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun ConversationNavigation(node: ConversationNodeComponent) {
    val childStack by node.childrenState.subscribeAsState()
    val activeConfigs by node.routerV2.routeConfigs.collectAsState()

    AdaptiveNavigationScaffold(
        navigationTargets =
            activeConfigs.map { config ->
                NavigationTarget(
                    id = config.id.toString(),
                    isSelected = false,
                    onClick = { node.openConversation(config) },
                    icon = { Text("💬") },
                    label = config.metadata.title ?: ""
                )
            },
        content = {
            childStack.firstOrNull()?.let { child ->
                ConversationScreen(child.instance.component)
            }
        }
    )
}

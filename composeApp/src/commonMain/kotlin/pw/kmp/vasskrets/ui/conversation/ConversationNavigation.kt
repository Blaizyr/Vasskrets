@file:OptIn(ExperimentalUuidApi::class)

package pw.kmp.vasskrets.ui.conversation

import AdaptiveNavigationScaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import pw.kmp.vasskrets.components.conversation.ConversationNodeComponent
import pw.kmp.vasskrets.domain.conversation.model.ConversationDestinationConfig
import pw.kmp.vasskrets.navigation.NavItem
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun ConversationNavigation(node: ConversationNodeComponent) {
    val children by node.childrenValue.subscribeAsState()
    val availableConversations by node.controller.availableItems.collectAsState()

    val navItems = buildList {
        add(
            NavItem(
                id = "new",
                isSelected = false,
                onClick = { node.createNewConversation() },
                icon = { Text("➕") },
                label = "New"
            )
        )
        addAll(
            availableConversations.map { conversation ->
                NavItem(
                    id = conversation.id.toString(),
                    isSelected = false,
                    onClick = {
                        node.openConversation(
                            ConversationDestinationConfig(
                                conversationUuid = conversation.id
                            )
                        )
                    },
                    icon = { Text("💬") },
                    label = conversation.metadata.title.orEmpty()
                )
            }
        )
    }
    AdaptiveNavigationScaffold(
        navItems = navItems,
        content = {
            children.firstOrNull()?.let { child ->
                ConversationScreen(child.instance.component)
            }
        }
    )
}

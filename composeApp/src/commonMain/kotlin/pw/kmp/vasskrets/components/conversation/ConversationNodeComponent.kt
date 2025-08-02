package pw.kmp.vasskrets.components.conversation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.launch
import pw.kmp.vasskrets.components.Entry
import pw.kmp.vasskrets.createCoroutineScope
import pw.kmp.vasskrets.navigation.GenericNavigationDispatcher
import pw.kmp.vasskrets.platform.ConversationNavConfig
import pw.kmp.vasskrets.platform.ConversationRouterV2
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface ConversationNode {
    val context: ComponentContext
    val factory: ConversationComponentFactory
    val routerV2: ConversationRouterV2
    fun createNewConversation()
    fun closeConversation(id: Uuid)
}

@OptIn(ExperimentalUuidApi::class)
class ConversationNodeComponent(
    override val context: ComponentContext,
    override val factory: ConversationComponentFactory,
    override val routerV2: ConversationRouterV2,
    private val navigationDispatcher: GenericNavigationDispatcher<ConversationNavConfig, Entry.ConversationEntry>,
) : ConversationNode, ComponentContext by context, InstanceKeeper.Instance {

    private val conversationScope = context.lifecycle.createCoroutineScope()

    val routeConfigs = routerV2.routeConfigs
    val childrenState = navigationDispatcher.childrenState

    override fun createNewConversation() {
        onCreateNewConversation()
    }

    override fun closeConversation(id: Uuid) {
        conversationScope.launch {
            onCloseConversation(id)
        }
    }

    fun openConversation(config: ConversationNavConfig) {
        navigationDispatcher.open(config)
    }

    private fun onCreateNewConversation() {
        conversationScope.launch {
            val navConfig = routerV2.createNewConversation()
            navConfig?.let { navigationDispatcher.open(it) }
        }
    }

    private fun onCloseConversation(id: Uuid) {

    }
}

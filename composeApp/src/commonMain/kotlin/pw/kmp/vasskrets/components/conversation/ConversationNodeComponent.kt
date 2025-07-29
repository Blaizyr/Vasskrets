package pw.kmp.vasskrets.components.conversation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pw.kmp.vasskrets.components.Entry
import pw.kmp.vasskrets.platform.ConversationNavConfig
import pw.kmp.vasskrets.platform.ConversationRouterV2
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface ConversationNode {
    val context: ComponentContext
//    val router: ConversationRouter
    val routerV2: ConversationRouterV2
    fun createNewConversation()
    fun closeConversation(id: Uuid)
}

@OptIn(ExperimentalUuidApi::class)
class ConversationNodeComponent(
    override val context: ComponentContext,
//    override val router: ConversationRouter,
    override val routerV2: ConversationRouterV2,
//    private val strategy: NodeStrategy
) : ConversationNode, ComponentContext by context, InstanceKeeper.Instance {
    private val conversationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _conversations = MutableStateFlow<List<Entry.ConversationEntry>>(emptyList())
    val conversations: StateFlow<List<Entry.ConversationEntry>> = _conversations.asStateFlow()


    val navigation = StackNavigation<ConversationNavConfig>()
    val childStack = childStack(
        source = navigation,
        serializer = ConversationNavConfig.serializer(),
        initialConfiguration = routerV2.activeConfigs.value.firstOrNull() ?: throw IllegalStateException("Empty stack"),
        handleBackButton = true,
        childFactory = { config, _ ->
            val newComponent = routerV2.createComponent(config)
            _conversations.update { it }
            newComponent
        }
    )

    override fun createNewConversation() {
        onCreateNewConversation()
    }

    override fun closeConversation(id: Uuid) {
        conversationScope.launch {
            onCloseConversation(id)
        }
    }

    private fun onCreateNewConversation() {
        conversationScope.launch {
            routerV2.createNewConversation()
        }
    }

    private fun onCloseConversation(id: Uuid) {
        routerV2.closeConversation(id)
    }
}

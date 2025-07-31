package pw.kmp.vasskrets.components.conversation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import pw.kmp.vasskrets.components.Entry
import pw.kmp.vasskrets.navigation.GenericNavigationDispatcher
import pw.kmp.vasskrets.navigation.ViewIntent
import pw.kmp.vasskrets.platform.ConversationNavConfig
import pw.kmp.vasskrets.platform.ConversationRouterV2
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface ConversationNode {
    val context: ComponentContext
    val factory: ConversationComponentFactory
//    val router: ConversationRouter
    val routerV2: ConversationRouterV2
    fun createNewConversation()
    fun closeConversation(id: Uuid)
}

@OptIn(ExperimentalUuidApi::class)
class ConversationNodeComponent(
    override val context: ComponentContext,
    private val navigationDispatcher: GenericNavigationDispatcher<ConversationNavConfig, Entry.ConversationEntry>,
    override val factory: ConversationComponentFactory,
//    override val router: ConversationRouter,
    override val routerV2: ConversationRouterV2,
//    private val strategy: NodeStrategy
) : ConversationNode, ComponentContext by context, InstanceKeeper.Instance {
    private val conversationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)


    val routeConfigs = routerV2.routeConfigs
    val childrenState = navigationDispatcher.childrenState

    fun onIntent(intent: ViewIntent) {
        navigationDispatcher.dispatch(intent)
    }

/*    val navigation = StackNavigation<ConversationNavConfig>()

    val childStack = childStack(
        source = navigation,
        serializer = ConversationNavConfig.serializer(),
        initialConfiguration = routerV2.routeConfigs.value.first(),
        handleBackButton = true,
        childFactory = { config, _ ->
            val instance = factory(config.id)
            Entry.ConversationEntry(config.id, instance)
        }
    )*/


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

package pw.kmp.vasskrets.components.conversation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin
import pw.kmp.vasskrets.components.Entry
import pw.kmp.vasskrets.createCoroutineScope
import pw.kmp.vasskrets.navigation.GenericNavigationDispatcher
import pw.kmp.vasskrets.platform.ConversationNavConfig
import pw.kmp.vasskrets.platform.ConversationRouter
import pw.kmp.vasskrets.ui.windowing.WindowManager

interface ConversationNode {
    val context: ComponentContext
    val factory: ConversationComponentFactory
    val router: ConversationRouter
    fun createNewConversation()
    fun openConversation(config: ConversationNavConfig)
    fun closeConversation(config: ConversationNavConfig)
}

class ConversationNodeComponent(
    override val context: ComponentContext,
    override val factory: ConversationComponentFactory,
    override val router: ConversationRouter,
    private val navigationDispatcher: GenericNavigationDispatcher<ConversationNavConfig, Entry.ConversationEntry>,
) : ConversationNode, ComponentContext by context, InstanceKeeper.Instance {

    private val conversationScope = context.lifecycle.createCoroutineScope()

    private val windowManager = getKoin().get<WindowManager>()

    val routeConfigs = router.routeConfigs
    val childrenState = navigationDispatcher.childrenState

    override fun createNewConversation() {
        conversationScope.launch {
            onCreateNewConversation()
        }
    }

    override fun openConversation(config: ConversationNavConfig) {
        conversationScope.launch {
            navigationDispatcher.open(config)
        }
    }

    override fun closeConversation(config: ConversationNavConfig) {
        conversationScope.launch {
            onCloseConversation(config)
        }
    }

    private suspend fun onCreateNewConversation() {
        val navConfig = router.createNewConversation()
        navConfig?.let { navigationDispatcher.open(it) }
    }

    private fun onOpenConversation(config: ConversationNavConfig) {
        navigationDispatcher.open(config)
    }

    private fun onCloseConversation(config: ConversationNavConfig) {
        navigationDispatcher.close(config)
    }
}

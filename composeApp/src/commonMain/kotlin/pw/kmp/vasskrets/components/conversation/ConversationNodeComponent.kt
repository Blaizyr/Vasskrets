package pw.kmp.vasskrets.components.conversation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin
import pw.kmp.vasskrets.components.DomainComponentEntry
import pw.kmp.vasskrets.createCoroutineScope
import pw.kmp.vasskrets.domain.conversation.model.ConversationDestinationConfig
import pw.kmp.vasskrets.navigation.GenericNavigationDispatcher
import pw.kmp.vasskrets.platform.ConversationsController
import pw.kmp.vasskrets.platform.PlatformFamily
import pw.kmp.vasskrets.platform.platform
import pw.kmp.vasskrets.ui.windowing.WindowManager
import pw.kmp.vasskrets.ui.windowing.extension.scopedReactive
import kotlin.uuid.ExperimentalUuidApi

interface ConversationNode {
    val context: ComponentContext
    val factory: ConversationComponentFactory
    val controller: ConversationsController
    fun createNewConversation()
    fun openConversation(config: ConversationDestinationConfig)
    fun closeConversation(config: ConversationDestinationConfig)
}

@OptIn(ExperimentalDecomposeApi::class, ExperimentalUuidApi::class)
class ConversationNodeComponent(
    override val context: ComponentContext,
    override val factory: ConversationComponentFactory,
    override val controller: ConversationsController,
    private val navigationDispatcher: GenericNavigationDispatcher<ConversationDestinationConfig, DomainComponentEntry.ConversationEntry>,
) : ConversationNode, ComponentContext by context, InstanceKeeper.Instance {

    private val conversationScope = context.lifecycle.createCoroutineScope()

    private val windowManager = getKoin().get<WindowManager>()
    val conversationWindows = windowManager.scopedReactive<DomainComponentEntry.ConversationEntry>()

    val childrenState = navigationDispatcher.childrenValue

    override fun createNewConversation() {
        conversationScope.launch {
            onCreateNewConversation()
        }
    }

    override fun openConversation(config: ConversationDestinationConfig) {
        conversationScope.launch {
            onOpenConversation(config)
        }
    }

    override fun closeConversation(config: ConversationDestinationConfig) {
        conversationScope.launch {
            onCloseConversation(config)
        }
    }

    private suspend fun onCreateNewConversation() {
        val newConversationIdentity = controller.createNewConversation()
        newConversationIdentity?.let {
            navigationDispatcher.open(
                ConversationDestinationConfig(
                    conversationUuid = it.id
                )
            )
        }
    }

    private fun onOpenConversation(config: ConversationDestinationConfig) {
        navigationDispatcher.open(config)

        if (platform.family != PlatformFamily.JVM) return

        childrenState.value.find { it.key == config }?.let {
            windowManager.open(it.instance)
        } ?: println("Theres no entry for convo navi config: $config")
    }


    private fun onCloseConversation(config: ConversationDestinationConfig) {
        navigationDispatcher.close(config)
    }
}

package pw.kmp.vasskrets.components.conversation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import pw.kmp.vasskrets.platform.ConversationRouter
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface ConversationNode {
    val nodeComponentContext: ComponentContext
    val router: ConversationRouter
    fun createNewConversation()
    fun closeConversation(id: Uuid)
}

@OptIn(ExperimentalUuidApi::class)
class ConversationNodeComponent(
    override val nodeComponentContext: ComponentContext,
    override val router: ConversationRouter,
    private val strategy: NodeStrategy
) : ConversationNode, ComponentContext by nodeComponentContext, InstanceKeeper.Instance {
    private val conversationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun createNewConversation() {
        onCreateNewConversation()
    }

    override fun closeConversation(id: Uuid) {
        conversationScope.launch {
            onCloseConversation(id)
        }
    }

    private fun onCreateNewConversation() {
        router.createNewConversation()
    }

    private fun onCloseConversation(id: Uuid) {
        router.closeConversation(id)
    }
}

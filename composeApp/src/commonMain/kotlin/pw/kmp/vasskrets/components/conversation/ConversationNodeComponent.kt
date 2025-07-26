package pw.kmp.vasskrets.components.conversation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import pw.kmp.vasskrets.platform.ConversationRouter
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface ConversationNode {
    val router: ConversationRouter
    fun createNewConversation()
    fun closeConversation(id: Uuid)
}

@OptIn(ExperimentalUuidApi::class)
class ConversationNodeComponent(
    override val router: ConversationRouter
) : ConversationNode {
    private val conversationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun createNewConversation() {
        conversationScope.launch {
            onCreateNewConversation()
        }
    }

    override fun closeConversation(id: Uuid) {
        conversationScope.launch {
            onCloseConversation(id)
        }
    }

    private suspend fun onCreateNewConversation() {
        router.createNewConversation()
    }

    private fun onCloseConversation(id: Uuid) {
        router.closeConversation(id)
    }

}

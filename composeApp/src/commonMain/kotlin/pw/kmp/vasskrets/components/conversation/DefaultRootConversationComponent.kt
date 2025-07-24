@file:OptIn(ExperimentalUuidApi::class)

package pw.kmp.vasskrets.components.conversation

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import pw.kmp.vasskrets.domain.model.conversation.ConversationEntry
import pw.kmp.vasskrets.domain.usecase.CreateNewConversationUseCase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface RootConversationComponent {
    val conversations: StateFlow<List<ConversationEntry>>
    fun createNewConversation()
    fun closeConversation(id: Uuid)
}

class DefaultRootConversationComponent(
    componentContext: ComponentContext,
    private val createNewConversationUseCase: CreateNewConversationUseCase,
    private val conversationFactory: ConversationComponentFactory,
) : RootConversationComponent, ComponentContext by componentContext {
    private val rootConversationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val _conversations = MutableStateFlow<List<ConversationEntry>>(emptyList())
    override val conversations = _conversations.asStateFlow()

    override fun createNewConversation() {
        rootConversationScope.launch {
            onCreateNewConversation()
        }
    }

    override fun closeConversation(id: Uuid) {
        rootConversationScope.launch {
            onCloseConversation(id)
        }
    }

    private suspend fun onCreateNewConversation() {
        val newConversationId = createNewConversationUseCase()
        val conversationComponent =
            conversationFactory.create(newConversationId)
        _conversations.value += ConversationEntry(
            id = newConversationId,
            component = conversationComponent
        )
    }

    private fun onCloseConversation(id: Uuid) {
        _conversations.update { _conversations.value.filterNot { it.id == id } }
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data class Conversation(val conversationId: Uuid) : Config
    }
}

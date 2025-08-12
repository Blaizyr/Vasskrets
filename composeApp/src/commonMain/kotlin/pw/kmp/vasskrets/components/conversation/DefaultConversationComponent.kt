package pw.kmp.vasskrets.components.conversation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pw.kmp.vasskrets.domain.conversation.model.ConversationInputElement
import pw.kmp.vasskrets.domain.conversation.model.ConversationMessage
import pw.kmp.vasskrets.domain.conversation.model.Participant
import pw.kmp.vasskrets.domain.conversation.usecase.SendTextMessageUseCase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class ConversationState(
    val conversationId: Uuid,
    val messages: List<ConversationMessage> = emptyList(),
    val currentTextInput: String = "",
    val attachments: List<ConversationInputElement>? = emptyList(),
)

interface ConversationComponent {
    val componentContext: ComponentContext
    val uiState: StateFlow<ConversationState>
    val onTextInputChanged: (String) -> Unit
    fun sendMessage()
}

@OptIn(ExperimentalUuidApi::class)
class DefaultConversationComponent(
    private val conversationId: Uuid,
    private val sendTextMessageUseCase: SendTextMessageUseCase,
    override val componentContext: ComponentContext,
) : ConversationComponent, ComponentContext by componentContext, InstanceKeeper.Instance {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _uiState = MutableStateFlow(ConversationState(conversationId))
    override val uiState: StateFlow<ConversationState> = _uiState.asStateFlow()

    override val onTextInputChanged: (String) -> Unit = { text ->
        coroutineScope.launch {
            _uiState.update { it.copy(currentTextInput = text) }
        }
    }

    override fun sendMessage() {
        coroutineScope.launch {
            onSendMessage()
        }
    }
    private fun onSendMessage() {
        sendTextMessageUseCase.invoke(
            conversationId,
            Participant.USER,
            uiState.value.currentTextInput
        )
    }
}

package pw.kmp.vasskrets.components.conversation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pw.kmp.vasskrets.domain.conversation.model.Participant
import pw.kmp.vasskrets.domain.conversation.usecase.SendTextMessageUseCase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class ConversationState(
    val conversationId: Uuid,
    val text: String = "",
)

interface ConversationComponent {
    val uiState: StateFlow<ConversationState>
    fun sendMessage()
}

@OptIn(ExperimentalUuidApi::class)
class DefaultConversationComponent(
    componentContext: ComponentContext,
    private val conversationId: Uuid,
    private val sendTextMessageUseCase: SendTextMessageUseCase,
) : ConversationComponent, ComponentContext by componentContext, InstanceKeeper.Instance {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _uiState = MutableStateFlow(ConversationState(conversationId = conversationId))
    override val uiState: StateFlow<ConversationState> = _uiState.asStateFlow()

    override fun sendMessage() {
        coroutineScope.launch {
            onSendMessage()
        }
    }

    private suspend fun onSendMessage() {
        sendTextMessageUseCase.invoke(conversationId, Participant.USER, uiState.value.text)
    }
}

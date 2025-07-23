package pw.kmp.vasskrets.components.conversation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class ConversationState(
    val conversationId: Uuid,
)

interface ConversationComponent {
    val uiState: StateFlow<ConversationState>
}

@OptIn(ExperimentalUuidApi::class)
class DefaultConversationComponent(
    componentContext: ComponentContext
) : ConversationComponent, ComponentContext by componentContext, InstanceKeeper.Instance {

    private val _uiState = MutableStateFlow(ConversationState(conversationId = Uuid.random()))
    override val uiState: StateFlow<ConversationState> = _uiState.asStateFlow()

}
